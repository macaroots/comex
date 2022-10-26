package br.com.alura.comex.config.validacao.dto;

public class ErroFormDto {
    public String campo;
    private String erro;

    public ErroFormDto(String campo, String mensagem) {
        setCampo(campo);
        setErro(mensagem);
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
