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
 * Solo disponible con profile "real".
 * Ejecuta queries SELECT directos para validar acceso a datos.
 */
@RestController
@RequestMapping("/db-test")
@Profile("real")
@Tag(name = "DB Test", description = "Endpoints de prueba de conexión a Oracle (solo profile real)")
public class DbTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Verifica la conexión a Oracle con un SELECT simple.
     */
    @GetMapping("/ping")
    @Operation(summary = "Ping DB", description = "Verifica conexión a Oracle con SELECT 1 FROM DUAL")
    public ResponseEntity<Map<String, Object>> ping() {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            String dbVersion = jdbcTemplate.queryForObject(
                "SELECT banner FROM v$version WHERE ROWNUM = 1", String.class);
            result.put("status", "CONECTADO");
            result.put("database", dbVersion);
            result.put("schema", "rvt");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("message", e.getMessage());
            return ResponseEntity.status(503).body(result);
        }
    }

    /**
     * Lista las primeras 10 pólizas de la tabla POLIZA.
     */
    @GetMapping("/polizas")
    @Operation(summary = "Listar pólizas Oracle", description = "SELECT de las primeras 10 pólizas de la tabla POLIZA")
    public ResponseEntity<List<Map<String, Object>>> listarPolizas() {
        List<Map<String, Object>> polizas = jdbcTemplate.queryForList(
            "SELECT NUMERO_INTERNO, NUMERO_POLIZA, ESTADO_POLIZA, " +
            "IDENTIFICACION_AFILIADO, VALOR_PRIMA_UNICA, VALOR_BASICO_RENTA, " +
            "FECHA_EXPEDICION, MODALIDAD_PENSION, CLASE_POLIZA, TIPO_RENTA " +
            "FROM POLIZA WHERE ROWNUM <= 10 ORDER BY NUMERO_INTERNO");
        return ResponseEntity.ok(polizas);
    }

    /**
     * Consulta una póliza por número interno.
     */
    @GetMapping("/polizas/{numeroInterno}")
    @Operation(summary = "Consultar póliza por número interno")
    public ResponseEntity<Map<String, Object>> consultarPoliza(@PathVariable int numeroInterno) {
        try {
            Map<String, Object> poliza = jdbcTemplate.queryForMap(
                "SELECT NUMERO_INTERNO, NUMERO_POLIZA, ESTADO_POLIZA, " +
                "IDENTIFICACION_AFILIADO, VALOR_PRIMA_UNICA, VALOR_BASICO_RENTA, " +
                "VALOR_MESADA, FECHA_EXPEDICION, FECHA_VIGENCIA, " +
                "MODALIDAD_PENSION, CLASE_POLIZA, TIPO_RENTA, CAUSA_RENTA, " +
                "NUMERO_BENEFICIARIOS, OBSERVACIONES " +
                "FROM POLIZA WHERE NUMERO_INTERNO = ?", numeroInterno);
            return ResponseEntity.ok(poliza);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lista beneficiarios de una póliza por número interno.
     */
    @GetMapping("/beneficiarios/{numeroInterno}")
    @Operation(summary = "Listar beneficiarios por número interno de póliza")
    public ResponseEntity<List<Map<String, Object>>> listarBeneficiarios(@PathVariable int numeroInterno) {
        List<Map<String, Object>> beneficiarios = jdbcTemplate.queryForList(
            "SELECT SECUENCIA_BENEFICIARIO, TIPO_DOCUMENTO, IDENTIFICACION_BENEFICIARIO, " +
            "NOMBRE_COMUN, PARENTESCO, PORCENTAJE_BENEFICIARIO, ESTADO_BENEFICIARIO, " +
            "SEXO, FECHA_NACIMIENTO " +
            "FROM BENEFICIARIO WHERE NUMERO_INTERNO = ? AND SENAL_ACTIVO = 'S'",
            numeroInterno);
        return ResponseEntity.ok(beneficiarios);
    }

    /**
     * Lista los primeros 10 clientes.
     */
    @GetMapping("/clientes")
    @Operation(summary = "Listar clientes Oracle", description = "SELECT de los primeros 10 clientes")
    public ResponseEntity<List<Map<String, Object>>> listarClientes() {
        List<Map<String, Object>> clientes = jdbcTemplate.queryForList(
            "SELECT NIT, TIPO_PERSONA, TIPO_DOCUMENTO, IDENTIFICACION_FISCAL, " +
            "NOMBRE_COMUN, TIPO_CLIENTE " +
            "FROM CLIENTES WHERE ROWNUM <= 10");
        return ResponseEntity.ok(clientes);
    }

    /**
     * Cuenta registros en las tablas principales.
     */
    @GetMapping("/stats")
    @Operation(summary = "Estadísticas de tablas", description = "Cuenta registros en tablas principales")
    public ResponseEntity<Map<String, Object>> stats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        try {
            stats.put("polizas", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM POLIZA", Integer.class));
            stats.put("beneficiarios", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM BENEFICIARIO", Integer.class));
            stats.put("clientes", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM CLIENTES", Integer.class));
            stats.put("parametro_poliza", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PARAMETRO_POLIZA", Integer.class));
            stats.put("status", "OK");
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            stats.put("status", "ERROR");
            stats.put("message", e.getMessage());
            return ResponseEntity.status(503).body(stats);
        }
    }
}
