package com.torneos.torneos.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long usuarioId;
    private String nombreUsuario;
    private String rol;
}
