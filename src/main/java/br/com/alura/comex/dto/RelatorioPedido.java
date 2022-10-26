package br.com.alura.comex.dto;

import java.math.BigDecimal;

public interface RelatorioPedido {
    String getCategoria();
    Integer getQuantidade();
    BigDecimal getTotal();

}
