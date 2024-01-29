package com.msvc.user.msvcuser;


import java.io.BufferedReader;
		import java.io.InputStreamReader;
		import java.io.OutputStream;
		import java.net.HttpURLConnection;
		import java.net.URL;
		import com.google.gson.Gson;

public class Main {
	private static final String API_URL = "https://65b79bf746324d531d550a89.mockapi.io/api/v1/";

	public static void main(String[] args) {
		// Crear un perfil de usuario
		Usuario usuario = new Usuario("Juan", "Perez", "juan@google.com", "123456789", "Medellin", "Colombia");

		// Crear el perfil en la API REST
		crearPerfil(usuario);

		// Mostrar el perfil en la página de perfil
		mostrarPerfil();
	}

	public static void crearPerfil(Usuario usuario) {
		try {
			URL url = new URL(API_URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);

			Gson gson = new Gson();
			String jsonInputString = gson.toJson(usuario);

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			con.getResponseCode(); // Esto envía la solicitud

			System.out.println("Perfil creado con éxito.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mostrarPerfil() {
		try {
			URL url = new URL(API_URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();

			// Convertir la respuesta JSON a un objeto Usuario
			Gson gson = new Gson();
			Usuario usuario = gson.fromJson(content.toString(), Usuario.class);

			// Mostrar el perfil en la consola
			System.out.println("Perfil:");
			System.out.println("Nombre: " + usuario.getNombre());
			System.out.println("Apellidos: " + usuario.getApellidos());
			System.out.println("Email: " + usuario.getEmail());
			System.out.println("Número de teléfono: " + usuario.getTelefono());
			System.out.println("Ciudad: " + usuario.getCiudad());
			System.out.println("País: " + usuario.getPais());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Usuario {
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;
	private String ciudad;
	private String pais;

	public Usuario(String nombre, String apellidos, String email, String telefono, String ciudad, String pais) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.telefono = telefono;
		this.ciudad = ciudad;
		this.pais = pais;
	}

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}

