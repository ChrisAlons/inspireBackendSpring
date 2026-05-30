package org.inspire.backend.modules.plantratamiento;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.catalogo.caradental.CaraDental;
import org.inspire.backend.catalogo.caradental.CaraDentalRepository;
import org.inspire.backend.catalogo.piezadental.PiezaDental;
import org.inspire.backend.catalogo.piezadental.PiezaDentalRepository;
import org.inspire.backend.catalogo.procedimiento.Procedimiento;
import org.inspire.backend.catalogo.procedimiento.ProcedimientoRepository;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.plantratamiento.dto.CreatePlanDetalleDto;
import org.inspire.backend.modules.plantratamiento.dto.PlanDetalleResponse;
import org.inspire.backend.modules.plantratamiento.dto.UpdatePlanDetalleDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class PlanTratamientoDetalleService {

    private final PlanTratamientoDetalleRepository detalleRepo;
    private final PlanTratamientoRepository planRepo;
    private final ProcedimientoRepository procedimientoRepo;
    private final PiezaDentalRepository piezaRepo;
    private final CaraDentalRepository caraRepo;

    @Transactional
    public PlanDetalleResponse crear(UUID planId, @Valid CreatePlanDetalleDto dto) {
        PlanTratamiento plan = planRepo.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Plan de tratamiento no encontrado: " + planId));

        Procedimiento procedimiento = procedimientoRepo.findByCodigo(dto.procedimientoCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Procedimiento no encontrado: " + dto.procedimientoCodigo()));

        PiezaDental pieza = piezaRepo.findById(dto.piezaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pieza dental no encontrada: " + dto.piezaId()));

        CaraDental cara = caraRepo.findByCodigo(dto.caraCodigo())
                .orElseThrow(() -> new BusinessException(
                        "Cara dental no encontrada: " + dto.caraCodigo()));

        PlanTratamientoDetalle detalle = new PlanTratamientoDetalle();
        detalle.setPlanTratamiento(plan);
        detalle.setProcedimiento(procedimiento);
        detalle.setPieza(pieza);
        detalle.setCara(cara);
        detalle.setCantidad(dto.cantidad());
        detalle.setPrecioUnitario(dto.precioUnitario());
        detalle.setOrden(dto.orden() != null ? dto.orden() : 1);
        detalle.setNotas(dto.notas() != null ? dto.notas() : "");

        PlanTratamientoDetalle saved = detalleRepo.save(detalle);

        recalcularMontoTotal(plan);

        return PlanTratamientoDetalleMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PlanDetalleResponse obtenerPorId(UUID id) {
        PlanTratamientoDetalle detalle = detalleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Detalle de plan no encontrado: " + id));
        return PlanTratamientoDetalleMapper.toResponse(detalle);
    }

    @Transactional(readOnly = true)
    public List<PlanDetalleResponse> listarPorPlan(UUID planId) {
        List<PlanTratamientoDetalle> detalles = detalleRepo.findByPlanTratamientoIdOrderByOrden(planId);
        return detalles.stream()
                .map(PlanTratamientoDetalleMapper::toResponse)
                .toList();
    }

    @Transactional
    public PlanDetalleResponse actualizar(UUID id, UpdatePlanDetalleDto dto) {
        PlanTratamientoDetalle detalle = detalleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Detalle de plan no encontrado: " + id));

        if (dto.cantidad() != null) detalle.setCantidad(dto.cantidad());
        if (dto.precioUnitario() != null) detalle.setPrecioUnitario(dto.precioUnitario());
        if (dto.orden() != null) detalle.setOrden(dto.orden());
        if (dto.notas() != null) detalle.setNotas(dto.notas());

        PlanTratamientoDetalle updated = detalleRepo.save(detalle);

        recalcularMontoTotal(detalle.getPlanTratamiento());

        return PlanTratamientoDetalleMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        PlanTratamientoDetalle detalle = detalleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Detalle de plan no encontrado: " + id));

        PlanTratamiento plan = detalle.getPlanTratamiento();
        detalleRepo.delete(detalle);

        recalcularMontoTotal(plan);
    }

    private void recalcularMontoTotal(PlanTratamiento plan) {
        List<PlanTratamientoDetalle> detalles = detalleRepo.findByPlanTratamientoIdOrderByOrden(plan.getId());
        BigDecimal total = detalles.stream()
                .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        plan.setMontoTotal(total);
        planRepo.save(plan);
    }
}
