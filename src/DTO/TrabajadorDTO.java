package DTO;
//clase DTO de trabajador

public class TrabajadorDTO {
    private String nombre,idTrabajador;
    private String usuario;
    private String puesto;
    private String hrEntrada,hrComida,hrSalida;
    private String password;

    public DirectorioEmpresarialDTO getDirEmpresarial() {
        return dirEmpresarial;
    }

    public void setDirEmpresarial(DirectorioEmpresarialDTO dirEmpresarial) {
        this.dirEmpresarial = dirEmpresarial;
    }

    public DirectorioPersonalDTO getDirPersonal() {
        return dirPersonal;
    }

    public void setDirPersonal(DirectorioPersonalDTO dirPersonal) {
        this.dirPersonal = dirPersonal;
    }
    private DirectorioEmpresarialDTO dirEmpresarial;
    private DirectorioPersonalDTO dirPersonal;
    
    public TrabajadorDTO(){
        this.nombre="";
        this.idTrabajador="";
        this.usuario="";
        this.puesto="";
        this.hrEntrada="";
        this.hrComida="";
        this.hrSalida="";
        this.password="";
    }
    public TrabajadorDTO(String nombre,String idTrabajador,String usuario,String puesto, String hrEntrada,String hrComida,String hrSalida,String password){
            
        this.nombre=nombre;
        this.idTrabajador=idTrabajador;
        this.usuario=usuario;
        this.puesto=puesto;
        this.hrEntrada=hrEntrada;
        this.hrComida=hrComida;
        this.hrSalida=hrSalida;
        this.password=password;
    } 

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getHrEntrada() {
        return hrEntrada;
    }

    public void setHrEntrada(String hrEntrada) {
        this.hrEntrada = hrEntrada;
    }

    public String getHrComida() {
        return hrComida;
    }

    public void setHrComida(String hrComida) {
        this.hrComida = hrComida;
    }

    public String getHrSalida() {
        return hrSalida;
    }

    public void setHrSalida(String hrSalida) {
        this.hrSalida = hrSalida;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Trabajador{" + "nombre=" + nombre + ",idTrabajador=" + idTrabajador + ", usuario=" + usuario +
                "puesto=" +puesto + ", hrEntrada=" + hrEntrada + ", hrComida=" + hrComida + ", hrSalida=" +hrSalida +
                ", password=" + password + '}';
    }
    
}
