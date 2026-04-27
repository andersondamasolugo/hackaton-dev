package com.segurosbolivar.rvt.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller de prueba para verificar conexión directa a Oracle.
 * Filtra por NIT_NEGOCIO = 72 (Ramo Rentas Voluntarias).
 */
@RestController
@RequestMapping("/db-test")
@Profile("real")
@Tag(name = "DB Test", description = "Endpoints de prueba — Ramo 72 (RVT)")
public class DbTestController {

    private static final int RAMO_RVT = 72;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/ping")
    @Operation(summary = "Ping DB")
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            String dbVersion = jdbcTemplate.queryForObject(
                "SELECT banner FROM v$version WHERE ROWNUM = 1", String.class);
            result.put("status", "CONECTADO");
            result.put("database", dbVersion);
            result.put("ramo", RAMO_RVT);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
            return ResponseEntity.status(503).body(result);
        }
    }

    @GetMapping("/polizas")
    @Operation(summary = "Listar pólizas Ramo 72")
    public ResponseEntity<List<Map<String, Object>>> listarPolizas() {
        List<Map<String, Object>> polizas = jdbcTemplate.queryForList(
            "SELECT NUMERO_INTERNO, NUMERO_POLIZA, ESTADO_POLIZA, " +
            "IDENTIFICACION_AFILIADO, VALOR_PRIMA_UNICA, VALOR_BASICO_RENTA, " +
            "FECHA_EXPEDICION, MODALIDAD_PENSION, CLASE_POLIZA, TIPO_RENTA " +
            "FROM POLIZA WHERE NIT_NEGOCIO = ? AND ROWNUM <= 10 ORDER BY NUMERO_INTERNO",
            RAMO_RVT);
        return ResponseEntity.ok(polizas);
    }

    @GetMapping("/polizas/{numeroInterno}")
    @Operation(summary = "Consultar póliza Ramo 72 por número interno")
    public ResponseEntity<Map<String, Object>> consultarPoliza(@PathVariable int numeroInterno) {
        try {
            Map<String, Object> poliza = jdbcTemplate.queryForMap(
                "SELECT NUMERO_INTERNO, NUMERO_POLIZA, ESTADO_POLIZA, " +
                "IDENTIFICACION_AFILIADO, VALOR_PRIMA_UNICA, VALOR_BASICO_RENTA, " +
                "VALOR_MESADA, FECHA_EXPEDICION, FECHA_VIGENCIA, " +
                "MODALIDAD_PENSION, CLASE_POLIZA, TIPO_RENTA, CAUSA_RENTA, " +
                "NUMERO_BENEFICIARIOS, OBSERVACIONES " +
                "FROM POLIZA WHERE NIT_NEGOCIO = ? AND NUMERO_INTERNO = ?",
                RAMO_RVT, numeroInterno);
            return ResponseEntity.ok(poliza);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/beneficiarios/{numeroInterno}")
    @Operation(summary = "Listar beneficiarios Ramo 72")
    public ResponseEntity<List<Map<String, Object>>> listarBeneficiarios(@PathVariable int numeroInterno) {
        List<Map<String, Object>> beneficiarios = jdbcTemplate.queryForList(
            "SELECT SECUENCIA_BENEFICIARIO, TIPO_DOCUMENTO, IDENTIFICACION_BENEFICIARIO, " +
            "NOMBRE_COMUN, PARENTESCO, PORCENTAJE_BENEFICIARIO, ESTADO_BENEFICIARIO, " +
            "SEXO, FECHA_NACIMIENTO " +
            "FROM BENEFICIARIO WHERE NIT_NEGOCIO = ? AND NUMERO_INTERNO = ? AND SENAL_ACTIVO = 'S'",
            RAMO_RVT, numeroInterno);
        return ResponseEntity.ok(beneficiarios);
    }

    @GetMapping("/clientes")
    @Operation(summary = "Listar clientes Oracle")
    public ResponseEntity<List<Map<String, Object>>> listarClientes() {
        List<Map<String, Object>> clientes = jdbcTemplate.queryForList(
            "SELECT NIT, TIPO_PERSONA, TIPO_DOCUMENTO, IDENTIFICACION_FISCAL, " +
            "NOMBRE_COMUN, TIPO_CLIENTE " +
            "FROM CLIENTES WHERE ROWNUM <= 10");
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/stats")
    @Operation(summary = "Stats Ramo 72")
    public ResponseEntity<Map<String, Object>> stats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        try {
            stats.put("polizas_ramo72", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM POLIZA WHERE NIT_NEGOCIO = ?", Integer.class, RAMO_RVT));
            stats.put("beneficiarios_ramo72", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM BENEFICIARIO WHERE NIT_NEGOCIO = ?", Integer.class, RAMO_RVT));
            stats.put("clientes_total", jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CLIENTES", Integer.class));
            stats.put("ramo", RAMO_RVT);
            stats.put("status", "OK");
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            stats.put("status", "ERROR");
            stats.put("message", e.getMessage());
            return ResponseEntity.status(503).body(stats);
        }
    }
}
