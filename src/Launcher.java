import presentacion.Modelo;
public class Launcher {
    //Atributos
    private Modelo miApp;
    
    //Metodos
    public Launcher() {
        miApp = new Modelo();
        miApp.iniciar();        
    }
    
    public static void main(String[] args) {
        new Launcher();
    }
}