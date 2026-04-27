package com.segurosbolivar.rvt.infrastructure.adapter.in.rest;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Controller de aterrizaje con links a Swagger UI y endpoint Hola Mundo.
 */
@Controller
public class HomeController {

    /**
     * Página de aterrizaje HTML con links a Swagger y Hola Mundo.
     */
    @Hidden
    @GetMapping(value = "/", produces = "text/html")
    @ResponseBody
    public String home() {
        return """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>RVT Backend — Seguros Bolívar</title>
                <style>
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    body {
                        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                        background: linear-gradient(135deg, #1a237e 0%, #0d47a1 50%, #01579b 100%);
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        color: #fff;
                    }
                    .container {
                        text-align: center;
                        padding: 3rem;
                        background: rgba(255,255,255,0.1);
                        border-radius: 16px;
                        backdrop-filter: blur(10px);
                        border: 1px solid rgba(255,255,255,0.2);
                        max-width: 500px;
                        width: 90%;
                    }
                    h1 { font-size: 1.8rem; margin-bottom: 0.5rem; }
                    .subtitle { opacity: 0.8; margin-bottom: 2rem; font-size: 0.95rem; }
                    .links { display: flex; flex-direction: column; gap: 1rem; }
                    a {
                        display: block;
                        padding: 1rem 2rem;
                        background: rgba(255,255,255,0.15);
                        border: 1px solid rgba(255,255,255,0.3);
                        border-radius: 8px;
                        color: #fff;
                        text-decoration: none;
                        font-size: 1.1rem;
                        transition: all 0.2s;
                    }
                    a:hover {
                        background: rgba(255,255,255,0.25);
                        transform: translateY(-2px);
                    }
                    .emoji { font-size: 1.4rem; margin-right: 0.5rem; }
                    .badge {
                        display: inline-block;
                        margin-top: 2rem;
                        padding: 0.3rem 0.8rem;
                        background: rgba(76,175,80,0.3);
                        border: 1px solid rgba(76,175,80,0.5);
                        border-radius: 20px;
                        font-size: 0.8rem;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>🏦 RVT Backend</h1>
                    <p class="subtitle">Rentas Voluntarias — Seguros Bolívar<br>Hackathon DEV 2026</p>
                    <div class="links">
                        <a href="swagger-ui.html">
                            <span class="emoji">📋</span> Swagger UI — Documentación API
                        </a>
                        <a href="hola">
                            <span class="emoji">👋</span> Hola Mundo — Health Check
                        </a>
                        <a href="h2-console" target="_blank">
                            <span class="emoji">🗄️</span> H2 Console (solo profile mock)
                        </a>
                    </div>
                    <div class="badge">✅ Profile activo: mock</div>
                </div>
            </body>
            </html>
            """;
    }

    /**
     * Endpoint Hola Mundo para verificar que el backend responde.
     */
    @GetMapping("/hola")
    @ResponseBody
    public Map<String, String> holaMundo() {
        return Map.of(
            "mensaje", "¡Hola Mundo! 👋",
            "servicio", "RVT Backend — Rentas Voluntarias",
            "estado", "activo",
            "version", "0.0.1-SNAPSHOT"
        );
    }
}
