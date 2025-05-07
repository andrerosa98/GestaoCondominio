public class Usuario{
    private int idCondomino;
    private String nomeCondomino;
    private String cpfCondomino;
    private String emailCondomino;
    private String senhaCondomino;
    private String telefoneCondomino;
    private boolean usuarioAutorizado;

    public Usuario(int idCondomino, String nomeCondomino, String cpfCondomino, String emailCondomino,
                   String senhaCondomino, String telefoneCondomino, boolean usuarioAutorizado) {
        this.idCondomino = idCondomino;
        this.nomeCondomino = nomeCondomino;
        this.cpfCondomino = cpfCondomino;
        this.emailCondomino = emailCondomino;
        this.senhaCondomino = senhaCondomino;
        this.telefoneCondomino = telefoneCondomino;
        this.usuarioAutorizado = usuarioAutorizado;
    }

    public Usuario() {
    }

    public int getIdCondomino() {
        return idCondomino;
    }

    public void setIdCondomino(int idCondomino) {
        this.idCondomino = idCondomino;
    }

    public String getNomeCondomino() {
        return nomeCondomino;
    }

    public void setNomeCondomino(String nomeCondomino) {
        this.nomeCondomino = nomeCondomino;
    }

    public String getCpfCondomino() {
        return cpfCondomino;
    }

    public void setCpfCondomino(String cpfCondomino) {
        this.cpfCondomino = cpfCondomino;
    }

    public String getEmailCondomino() {
        return emailCondomino;
    }

    public void setEmailCondomino(String emailCondomino) {
        this.emailCondomino = emailCondomino;
    }

    public String getSenhaCondomino() {
        return senhaCondomino;
    }

    public void setSenhaCondomino(String senhaCondomino) {
        this.senhaCondomino = senhaCondomino;
    }

    public String getTelefoneCondomino() {
        return telefoneCondomino;
    }

    public void setTelefoneCondomino(String telefoneCondomino) {
        this.telefoneCondomino = telefoneCondomino;
    }

    public boolean isUsuarioAutorizado() {
        return usuarioAutorizado;
    }

    public void setUsuarioAutorizado(boolean usuarioAutorizado) {
        this.usuarioAutorizado = usuarioAutorizado;
    }
}