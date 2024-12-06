/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ghost_top;

/**
 *
 * @author Martin Montes
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Jugador {
    private String nombreUsuario;
    private String contrasena;
    private int puntos;
    private int juegosJugados; // Nuevo atributo para contar los juegos jugados
    private List<String> registrosDeJuego;

    public Jugador(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.puntos = 0;
        this.juegosJugados = 0; // Inicializar juegos jugados
        this.registrosDeJuego = new ArrayList<>();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getJuegosJugados() {
        return juegosJugados; // Método para obtener juegos jugados
    }

    public void agregarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void incrementarJuegosJugados() {
        this.juegosJugados++; // Método para incrementar juegos jugados
    }

    public void agregarRegistroDeJuego(String registro) {
        if (registrosDeJuego.size() >= 10) {
            registrosDeJuego.remove(0); // Eliminar el registro más antiguo
        }
        registrosDeJuego.add(registro);
    }

    public List<String> getRegistrosDeJuego() {
        return registrosDeJuego;
    }

    public void cambiarNombreUsuario(String nuevoNombre) {
        this.nombreUsuario = nuevoNombre; // Método para cambiar el nombre de usuario
    }

    public void cambiarContrasena(String nuevaContrasena) {
        this.contrasena = nuevaContrasena; // Método para cambiar la contraseña
    }
}

class JuegoDeFantmas {
    private Jugador jugador1;
    private Jugador jugador2;
    private String[][] tablero;
    private int[] cantidadDeFantasmagóricosBuenos;
    private int[] cantidadDeFantasmagóricosMalos;
    private boolean juegoActivo;
    private boolean colocacionManual;
    private int dificultad;

    public JuegoDeFantmas(Jugador jugador1, Jugador jugador2, boolean colocacionManual, int dificultad) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tablero = new String[6][6];
        this.cantidadDeFantasmagóricosBuenos = new int[2]; // 0 para jugador1, 1 para jugador2
        this.cantidadDeFantasmagóricosMalos = new int[2];
        this.juegoActivo = true;
        this.colocacionManual = colocacionManual;
        this.dificultad = dificultad;
        inicializarTablero();
        colocarFantasmagóricos();
    }

    private void inicializarTablero() {
        // Inicializar el tablero con cadenas vacías
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                tablero[i][j] = "";
            }
        }
    }

    public void colocarFantasmagóricos() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalFantasmagóricos = (dificultad == 0) ? 8 : (dificultad == 1) ? 4 : 2;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < totalFantasmagóricos / 2; j++) {
                String tipoFantasma = (i == 0) ? "G" : "B"; // G para bueno, B para malo
                if (colocacionManual) {
                    System.out.println("Jugador " + (i + 1) + ", coloca tu " + tipoFantasma + "antasma (fila, columna): ");
                    int fila = scanner.nextInt();
                    int columna = scanner.nextInt();
                    while (fila < 0 || fila >= 6 || columna < 0 || columna >= 6 || !tablero[fila][columna].isEmpty()) {
                        System.out.println("Posición inválida. Intenta de nuevo: ");
                        fila = scanner.nextInt();
                        columna = scanner.nextInt();
                    }
                    tablero[fila ][columna] = tipoFantasma + (i == 0 ? (j + 1) : (j + 1 + totalFantasmagóricos / 2));
                } else {
                    int fila, columna;
                    do {
                        fila = random.nextInt(2); // Solo las dos primeras filas
                        columna = random.nextInt(6);
                    } while (!tablero[fila][columna].isEmpty());
                    tablero[fila][columna] = tipoFantasma + (i == 0 ? (j + 1) : (j + 1 + totalFantasmagóricos / 2));
                }
            }
        }
        cantidadDeFantasmagóricosBuenos[0] = cantidadDeFantasmagóricosBuenos[1] = totalFantasmagóricos / 2; // La mitad son buenos para cada jugador
        cantidadDeFantasmagóricosMalos[0] = cantidadDeFantasmagóricosMalos[1] = totalFantasmagóricos / 2; // La mitad son malos para cada jugador
    }

public void jugarJuego() {
    Scanner scanner = new Scanner(System.in);
    while (juegoActivo) {
        for (int i = 0; i < 2; i++) {
            System.out.println("Turno de " + (i == 0 ? jugador1.getNombreUsuario() : jugador2.getNombreUsuario()));
            mostrarTablero(i);
            System.out.println("Selecciona un fantasma para mover (fila, columna) o escribe 'rendirse' para rendirte: ");
            String input = scanner.nextLine();

            // Opción de rendirse
            if (input.equalsIgnoreCase("rendirse")) {
                if (confirmarRendicion(scanner)) {
                    System.out.println((i == 0 ? jugador1.getNombreUsuario() : jugador2.getNombreUsuario()) + " se ha rendido.");
                    // Asignar un punto al jugador que no se rinde
                    if (i == 0) {
                        jugador2.agregarPuntos(1); // Incrementar puntos del jugador 2
                        jugador2.agregarRegistroDeJuego("Victoria por rendición sobre " + jugador1.getNombreUsuario());
                        jugador2.incrementarJuegosJugados(); // Incrementar juegos jugados
                    } else {
                        jugador1.agregarPuntos(1); // Incrementar puntos del jugador 1
                        jugador1.agregarRegistroDeJuego("Victoria por rendición sobre " + jugador2.getNombreUsuario());
                        jugador1.incrementarJuegosJugados(); // Incrementar juegos jugados
                    }
                    juegoActivo = false;
                    break; // Salir del bucle de turnos
                } else {
                    System.out.println("Rendición cancelada. Continúa jugando.");
                    i--; // Mantener el turno actual si se cancela la rendición
                    continue;
                }
            }

            // Procesar la entrada del jugador
            String[] partes = input.split(",");
            if (partes.length != 2) {
                System.out.println("Entrada inválida. Intenta de nuevo.");
                i--; // Mantener el turno actual si la entrada es inválida
                continue;
            }

            int fila = Integer.parseInt(partes[0].trim());
            int columna = Integer.parseInt(partes[1].trim());

            // Validar selección
            while (fila < 0 || fila >= 6 || columna < 0 || columna >= 6 || tablero[fila][columna].isEmpty() || 
                   (i == 0 && tablero[fila][columna].startsWith("B")) || 
                   (i == 1 && tablero[fila][columna].startsWith("G"))) {
                System.out.println("Selección inválida. Intenta de nuevo: ");
                fila = scanner.nextInt();
                columna = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
            }

            // Mover fantasma
            System.out.println("Ingresa la nueva posición para mover (fila, columna): ");
            int nuevaFila = scanner.nextInt();
            int nuevaColumna = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            // Validar movimiento
            while (nuevaFila < 0 || nuevaFila >= 6 || nuevaColumna < 0 || nuevaColumna >= 6 || 
                   (Math.abs(nuevaFila - fila) + Math.abs(nuevaColumna - columna) != 1) || 
                   (!tablero[nuevaFila][nuevaColumna].isEmpty() && 
                   (i == 0 && tablero[nuevaFila][nuevaColumna].startsWith("G")) || 
                   (i == 1 && tablero[nuevaFila][nuevaColumna].startsWith("B")))) {
                System.out.println("Movimiento inválido. Intenta de nuevo: ");
                nuevaFila = scanner.nextInt();
                nuevaColumna = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
            }

            // Capturar fantasma si es aplicable
            if (!tablero[nuevaFila][nuevaColumna].isEmpty()) {
                String fantasmaCapturado = tablero[nuevaFila][nuevaColumna];
                if (i == 0 && fantasmaCapturado.startsWith("B")) {
                    cantidadDeFantasmagóricosMalos[1]--;
                    System.out.println("¡Has capturado un fantasma malo de " + jugador2.getNombreUsuario() + "!");
                } else if (i == 1 && fantasmaCapturado.startsWith("G")) {
                    cantidadDeFantasmagóricosBuenos[0]--;
                    System.out.println("¡Has capturado un fantasma bueno de " + jugador1.getNombreUsuario() + "!");
                }
            }

            // Mover el fantasma
            tablero[nuevaFila][nuevaColumna] = tablero[fila][columna];
            tablero[fila][columna] = "";
            // Verificar condiciones de victoria
            verificarCondicionesDeVictoria();
        }
    }
}

private boolean confirmarRendicion(Scanner scanner) {
    System.out.print(" Estas seguro de que deseas rendirte? (si/no): ");
    String confirmacion1 = scanner.nextLine();
    if (!confirmacion1.equalsIgnoreCase("si")) {
        return false; // Si la primera confirmación no es "si", se cancela la rendición
    }
    System.out.print("Por favor, confirma nuevamente escribiendo 'si' para rendirte: ");
    String confirmacion2 = scanner.nextLine();
    return confirmacion2.equalsIgnoreCase("si"); // Retorna verdadero si la segunda confirmación es "si"
} 
    private void mostrarTablero(int jugador) {
        for (String[] fila : tablero) {
            for (String celda : fila) {
                if ((jugador == 0 && celda.startsWith("B")) || (jugador == 1 && celda.startsWith("G"))) {
                    System.out.print(". "); // Ocultar fantasmas del oponente
                } else {
                    System.out.print((celda.isEmpty() ? "." : celda) + " ");
                }
            }
            System.out.println();
        }
    }

    private void verificarCondicionesDeVictoria() {
        
        if (cantidadDeFantasmagóricosBuenos[0] == 0) {
            System.out.println(jugador2.getNombreUsuario() + " gana al capturar todos los fantasmas buenos!");
            jugador2.agregarPuntos(1);
            jugador2.agregarRegistroDeJuego("Victoria sobre " + jugador1.getNombreUsuario() + " al capturar todos los fantasmas buenos.");
            jugador2.incrementarJuegosJugados(); // Incrementar juegos jugados
            juegoActivo = false;
        } else if (cantidadDeFantasmagóricosMalos[1] == 0) {
            System.out.println(jugador1.getNombreUsuario() + " gana al capturar todos los fantasmas malos!");
            jugador1.agregarPuntos(1);
            jugador1.agregarRegistroDeJuego("Victoria sobre " + jugador2.getNombreUsuario() + " al capturar todos los fantasmas malos.");
            jugador1.incrementarJuegosJugados(); // Incrementar juegos jugados
            juegoActivo = false;
        } else if (tablero[5][0].startsWith("G") || tablero[5][5].startsWith("G")) {
            System.out.println(jugador1.getNombreUsuario() + " gana al sacar un fantasma bueno!");
            jugador1.agregarPuntos(1);
            jugador1.agregarRegistroDeJuego("Victoria sobre " + jugador2.getNombreUsuario() + " al sacar un fantasma bueno.");
            jugador1.incrementarJuegosJugados(); // Incrementar juegos jugados
            juegoActivo = false;
        } else if (tablero[0][0].startsWith("G") || tablero[0][5].startsWith("G")) {
            System.out.println(jugador2.getNombreUsuario() + " gana al sacar un fantasma bueno!");
            jugador2.agregarPuntos(1);
            jugador2.agregarRegistroDeJuego("Victoria sobre " + jugador1.getNombreUsuario() + " al sacar un fantasma bueno.");
            jugador2.incrementarJuegosJugados(); // Incrementar juegos jugados
            juegoActivo = false;
        }
    }
}
public class MainFantasma {
    private static List<Jugador> jugadores = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println(" !Bienvenido al Juego de Fantasmas!");
            System.out.println("1. Registrar nuevo usuario");
            System.out.println("2. Jugar");
            System.out.println("3. Mostrar tabla de posiciones");
            System.out.println("4. Ver perfil");
            System.out.println("5. Reglas"); // Nueva opción para mostrar reglas
            System.out.println("6. Salir");
            System.out.print("Selecciona una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    registrarUsuario(scanner);
                    break;
                case 2:
                    if (jugadores.size() < 2) {
                        System.out.println("Se necesitan al menos 2 jugadores para jugar.");
                    } else {
                        jugarPartida(scanner);
                    }
                    break;
                case 3:
                    mostrarRanking();
                    break;
                case 4:
                    verPerfil(scanner);
                    break;
                case 5:
                    mostrarReglas(); // Llamar a la función para mostrar reglas
                    break;
                case 6:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion no valida. Intenta de nuevo.");
            }
        }
    }

    private static void registrarUsuario(Scanner scanner) {
        System.out.print("Ingresa el nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingresa la contraseña: ");
        String contrasena = scanner.nextLine();
        
        // Validar que el nombre de usuario sea único
        for (Jugador jugador : jugadores) {
            if (jugador.getNombreUsuario().equals(nombreUsuario)) {
                System.out.println("El nombre de usuario ya existe. Intenta con otro.");
                return;
            }
        }
        jugadores.add(new Jugador(nombreUsuario,contrasena));
        System.out.println("Usuario registrado exitosamente.");
    }

    private static void jugarPartida(Scanner scanner) {
        System.out.print("Selecciona el índice del Jugador 1 (0 a " + (jugadores.size() - 1) + "): ");
        int index1 = scanner.nextInt();
        System.out.print("Selecciona el índice del Jugador 2 (0 a " + (jugadores.size() - 1) + "): ");
        int index2 = scanner.nextInt();

        if (index1 == index2 || index1 < 0 || index1 >= jugadores.size() || index2 <  0 || index2 >= jugadores.size()) {
            System.out.println("Seleccion de jugadores inválida.");
            return;
        }

        Jugador jugador1 = jugadores.get(index1);
        Jugador jugador2 = jugadores.get(index2);

        System.out.print("Selecciona la dificultad (0: Normal, 1: Experto, 2: Genio): ");
        int dificultad = scanner.nextInt();
        System.out.print(" Quieres colocación manual? (true/false): ");
        boolean colocacionManual = scanner.nextBoolean();

        JuegoDeFantmas juego = new JuegoDeFantmas(jugador1, jugador2, colocacionManual, dificultad);
        juego.jugarJuego();
    }

    private static void mostrarRanking() {
        System.out.println("Tabla de posiciones:");
        jugadores.sort((j1, j2) -> Integer.compare(j2.getPuntos(), j1.getPuntos())); // Ordenar por puntos
        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombreUsuario() + " - Puntos: " + jugador.getPuntos());
        }
    }

    private static void verPerfil(Scanner scanner) {
        System.out.print("Selecciona el índice del jugador para ver el perfil (0 a " + (jugadores.size() - 1) + "): ");
      int index = scanner.nextInt();
      scanner.nextLine(); // Limpiar el buffer

        if (index < 0 || index >= jugadores.size()) {
            System.out.println("Índice de jugador inválido.");
            return;
        }

        Jugador jugador = jugadores.get(index);
        System.out.print("Ingresa la contraseña para acceder al perfil: ");
        String contrasena = scanner.nextLine();

        if (!jugador.getContrasena().equals(contrasena)) {
            System.out.println("Contraseña incorrecta. Acceso denegado.");
            return;
        }

        System.out.println("Perfil de " + jugador.getNombreUsuario() + ":");
        System.out.println("Puntos: " + jugador.getPuntos());
        System.out.println("Juegos jugados: " + jugador.getJuegosJugados());

        System.out.print(" Quieres cambiar el nombre de usuario? (si/no): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            System.out.print("Ingresa el nuevo nombre de usuario: ");
            String nuevoNombre = scanner.nextLine();
            jugador.cambiarNombreUsuario(nuevoNombre);
            System.out.println("Nombre de usuario cambiado a: " + nuevoNombre);
        }

        System.out.print(" Quieres cambiar la contraseña? (si/no): ");
        respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            System.out.println("Advertencia: El programa no se hace cargo de contraseñas olvidadas.");
            System.out.print(" Estás seguro de que deseas cambiar la contraseña? (si/no): ");
            String confirmacion = scanner.nextLine();
            if (confirmacion.equalsIgnoreCase("si")) {
                System.out.print("Ingresa la nueva contraseña: ");
                String nuevaContrasena = scanner.nextLine();
                jugador.cambiarContrasena(nuevaContrasena);
                System.out.println("Contraseña cambiada exitosamente.");
            } else {
                System.out.println("Cambio de contraseña cancelado.");
            }
        }

        System.out.print(" Quieres borrar este usuario? (si/no): ");
        respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            jugadores.remove(index);
            System.out.println("Usuario borrado exitosamente.");
        }
    }

    private static void mostrarReglas() {
        System.out.println("Reglas del juego:");
        System.out.println("1. Cada jugador coloca sus fantasmas en el tablero.");
        System.out.println("2. Los jugadores se turnan para mover sus fantasmas.");
        System.out.println("3. El objetivo es capturar los fantasmas del oponente.");
        System.out.println("4. Gana el jugador que capture todos los fantasmas del oponente o saque un fantasma bueno.");
    }
}
  