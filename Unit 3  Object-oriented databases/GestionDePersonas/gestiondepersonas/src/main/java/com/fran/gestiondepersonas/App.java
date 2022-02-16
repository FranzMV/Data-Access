package com.fran.gestiondepersonas;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fran.gestiondepersonas.manejadorbasedatos.ManejadorBaseDatos;

/**
 * @author Francisco David Manzanedo
 */
public class App 
{
	private static Scanner sc = new Scanner(System.in);
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static final int MIN_PHONE_NUMBER = 100000000;
	private static final int MAX_PHONE_NUMBER = 999999999;
	private static final int NAMES_MAX_LENGTH = 30;
	private static final int SURNAMES_MAX_LENGTH = 60;
	private static final int ADDRESS_MAX_LENGTH = 100;
	private static final int DEPARTMENT_MAX_LENGTH = 20;
	private static final int CODE_GROUP_MAX_LENGTH = 5;
	private static final int BANK_ACCOUNT_MAX_LENGTH = 24;
	private static final String CLIENT_STATE_PENDING ="pendiente";
	private static final String CLIENT_STATE_ACTIVE = "activo";
	//private static final String CLIENT_STATE_INACTIVE = "inactivo";
	private static final String NORMAL_CLIENT_TYPE ="normal";
	private static final String PREMIUM_CLIENT_TYPE ="premium";
	
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
       ManejadorBaseDatos manejador = new ManejadorBaseDatos();
		boolean exit = false;
		
		do
		{
			ShowMenu();
			switch (getOption()) {
			case 1: addPersona(manejador); break;
			case 2: addCliente(manejador); break;
			case 3: addFuncionario(manejador); break;
			case 4: modifyPersona(manejador); break;
			case 5: modifyCliente(manejador); break;
			case 6: modifyFuncionario(manejador); break;
			case 7: showPersonas(manejador); break;
			case 8: showClientes(manejador); break;
			case 9: showFuncionarios(manejador); break;
			case 0: exit = true; break;
			default: System.out.println("Opción no permitida."); break;
			}
		}
		while(!exit);
	}
	
	private static int getOption() {
		System.out.println("Option:");
		int option = sc.nextInt();
		sc.nextLine();
		return option;
	}

	private static void ShowMenu()
	{
		System.out.println("1.-Añadir persona");
		System.out.println("2.-Añadir cliente");
		System.out.println("3.-Añadir funcionario");
		System.out.println("4.-Modificar persona");
		System.out.println("5.-Modificar cliente");
		System.out.println("6.-Modificar funcionario");
		System.out.println("7.-Ver personas");
		System.out.println("8.-Ver clientes");
		System.out.println("9.-Ver funcionarios");
		System.out.println("0.-Salir");
	}
	
	//Añade un registro de persona a la base de datos
	private static void addPersona(ManejadorBaseDatos manejador) throws ClassNotFoundException, SQLException 
	{
		List<String> personas = addPersonasAux();
		
		final String SQL_INSERT_PERSONA ="INSERT INTO personas "
				+ "(nombres, apellidos, direccion, telefono, fecha_nacim) "
				+ "VALUES "
				+ "("
					+ "'"+personas.get(0)+ "', '"+personas.get(1)+"','"+personas.get(2)+"', "
					+Integer.parseInt(personas.get(3))+", date('"+personas.get(4)+"')"
				+ ");";
		
		manejador.update(SQL_INSERT_PERSONA);
	}

	//Pide y valida los datos de una persona
	private static List<String> addPersonasAux() {
		List<String> result = new ArrayList<String>();
		
		String name = validateString("Nombre: ",NAMES_MAX_LENGTH);
		result.add(name);
		String surnames = validateString("Apellidos: ", SURNAMES_MAX_LENGTH);
		result.add(surnames);
		String address = validateString("Dirección: ", ADDRESS_MAX_LENGTH);
		result.add(address);
		String phoneNumber = validatePhoneNumber("Teléfono: ");
		result.add(phoneNumber);
		LocalDate birthDate = validateLocalDate("Fecha de nacimiento (dd-MM-yyyy): ");
		result.add(birthDate.toString());
		
		return result;
	}
	
	//Añade un registro de cliente a la base de datos
	private static void addCliente(ManejadorBaseDatos manejador) throws ClassNotFoundException, SQLException 
	{
		List<String> clientes = addClienteAux();
		
		final String SQL_INSERT_CLIENTE= "INSERT INTO clientes "
				+ "(nombres, apellidos, direccion, telefono, fecha_nacim, nro_cuenta, "
				+ "estado, tipo_cliente) "
				+ "VALUES ("
				+ "'"+clientes.get(0)+ "', '"+clientes.get(1)+"','"+clientes.get(2)+"', "
				+Integer.parseInt(clientes.get(3))+", DATE('"+clientes.get(4)+"'), '"
				+clientes.get(5)+"', '"+clientes.get(6)+"', '"+clientes.get(7)+"');";
		
		System.out.println("Cliente añadido.");
		manejador.update(SQL_INSERT_CLIENTE);
	}
	
	
	//Pide y valida los datos de un cliente
	private static List<String> addClienteAux() {
		
		//Se añaden primero los datos base de personas
		List<String> result = addPersonasAux();
		
		//Datos de tipo Cliente
		String bankAccount = validateString("Número de cuenta: ",BANK_ACCOUNT_MAX_LENGTH);
		result.add(bankAccount);
		
		if(!validateIBAN(bankAccount))
		{
			System.out.println("El número de cuenta no ha sido verificado.");
			result.add(CLIENT_STATE_PENDING);
		}
		else result.add(CLIENT_STATE_ACTIVE);
		
		String tipoCliente;
		do
		{
			System.out.println("Tipo de cliente:");
			System.out.println("1. Normal");
			System.out.println("2. Premium");
			System.out.print("Elige una opción: ");
			tipoCliente = sc.nextLine();
			
			switch (tipoCliente) {
				case "1": result.add(NORMAL_CLIENT_TYPE); break;
				case "2": result.add(PREMIUM_CLIENT_TYPE); break;
				default: 
					System.out.println("Opción incorrecta, debe seleccionar cualquiera de los dos tipos."); 
					break;
			}
			
		} while(!tipoCliente.equals("1") && !tipoCliente.equals("2"));
		
		return result;
	}
	
	//Añade un registro de funcionario a la base de datos
	private static void addFuncionario(ManejadorBaseDatos manejador) throws ClassNotFoundException, SQLException {
		
		List<String> funcionarios = addFuncionarioAux();
		
		final String SQL_INSERT_FUNCIONARIO ="INSERT INTO funcionarios "
				+ "(nombres, apellidos, direccion, telefono, fecha_nacim, "
				+ "cargo, departamento, fecha_ingreso) "
				+ "VALUES ("
				+ "'"+funcionarios.get(0)+ "', '"+funcionarios.get(1)+"','"+funcionarios.get(2)+"', "
				+Integer.parseInt(funcionarios.get(3))+", DATE('"+funcionarios.get(4)+"'), "
				+ "('"+funcionarios.get(5)+"', '"+funcionarios.get(6)+"'), '"+funcionarios.get(7)+"', "
						+ "DATE('"+funcionarios.get(8)+"'));";
		
		System.out.println("Funcionario añadido");
		//funcionarios.forEach(e-> System.out.println(e));
		manejador.update(SQL_INSERT_FUNCIONARIO);
	}
	
	
	//Pide y valida los datos de un funcionario
	private static List<String> addFuncionarioAux(){
		
		//Se añaden primero los datos base de personas
		List<String> result = addPersonasAux();
		String option;
		boolean groupOk = false;
		//Datos de tipo funcionario
		do {
			
			System.out.println("Seleccione el grupo:");
			System.out.println("1. A1");
			System.out.println("2. A2");
			System.out.println("3. C1");
			System.out.println("4. C2");
			System.out.println("5. AP");
			option = sc.nextLine();
			
			switch (option) {
			case "1": result.add("A1");  groupOk = true; break; 
			case "2": result.add("A2");  groupOk = true; break;
			case "3": result.add("C1");  groupOk = true; break;
			case "4": result.add("C2");  groupOk = true; break;
			case "5": result.add("AP");  groupOk = true; break;

			default:
				System.out.println("Grupo incorrecto. Debe seleccionar un grupo válido.");
				groupOk = false;
				break;
			}
		} while (!groupOk);
		
		String codeGroup = validateString("Codigo del cuerpo (5 caracteres): ",CODE_GROUP_MAX_LENGTH);
		result.add(codeGroup);
		String department = validateString("Departamento: ", DEPARTMENT_MAX_LENGTH);
		result.add(department);
		LocalDate dateOfAdmission = validateLocalDate("Fecha de ingreso (dd-mm-yyyy): ");
		result.add(dateOfAdmission.toString());
		
		return result;
	}
	
	
	//Modifica un registro de persona
	private static void modifyPersona(ManejadorBaseDatos manejador) throws ClassNotFoundException, SQLException {
		
		boolean exitUpdate = false;
		String SQL_UPDATE_PERSONA;
		
		showPersonas(manejador);
		System.out.println("Seleccione el número de la persona que desea modificar: ");
		int number = sc.nextInt();
		sc.nextLine();
		
		do {
			
			System.out.println("Datos actuales de la persona seleccionada: ");
			modifyPersonaAux(manejador, number);
			System.out.println("Selecciona el registro que desea modificar (0 para terminar): ");
			String option = sc.nextLine();
			
			switch (option) {
			case "0": exitUpdate = true; break;
			
			case "1"://Nombres
				String newName = validateString("Nombres: ", NAMES_MAX_LENGTH);
				SQL_UPDATE_PERSONA = "UPDATE personas SET nombres = '"+newName+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_PERSONA);
				System.out.println("Nombre "+newName+" actualizado.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "2": //Apellidos 
				String newSurnames = validateString("Apellidos: ", SURNAMES_MAX_LENGTH);
				SQL_UPDATE_PERSONA = "UPDATE personas SET apellidos = '"+newSurnames+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_PERSONA);
				System.out.println("Apellidos "+newSurnames+" actualizado.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "3": //Dirección
				String newAddress = validateString("Dirección: ", ADDRESS_MAX_LENGTH);
				SQL_UPDATE_PERSONA = "UPDATE personas SET direccion = '"+newAddress+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_PERSONA);
				System.out.println("Dirección "+newAddress+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "4": //Télefono
				String newPhoneNum= validatePhoneNumber("Teléfono: ");
				SQL_UPDATE_PERSONA = "UPDATE personas SET telefono = "+Integer.parseInt(newPhoneNum)+" WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_PERSONA);
				System.out.println("Teléfono "+newPhoneNum+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "5": //Fecha nacimiento
				LocalDate newBirthDate = validateLocalDate("Fecha de nacimiento (dd-MM-yyyy): ");
				SQL_UPDATE_PERSONA = "UPDATE personas SET fecha_nacim = date('"+newBirthDate.toString()+"') WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_PERSONA);
				System.out.println("Fecha de nacimiento "
						+newBirthDate.getDayOfMonth()+"-"
						+newBirthDate.getMonthValue()+"-"
						+newBirthDate.getYear()+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			default:
				System.out.println("Opción no permitida. Seleccione el dato que desea modificar.");
				exitUpdate = false;
				break;
			}
		}while(!exitUpdate);
	}
	
	//Muestra los datos de una persona para ser editado el usuario
	private static void modifyPersonaAux(ManejadorBaseDatos manejador, int number) throws ClassNotFoundException, SQLException {
		
		final String SQL_SELECT_PERSONA = "SELECT * FROM personas WHERE numero = "+number+";";
		ResultSet rs = manejador.select(SQL_SELECT_PERSONA);
		if(rs.isBeforeFirst()) {
			
			while(rs.next()) {
				LocalDate birthDate = rs.getDate(6).toLocalDate();
				System.out.println("1.- Nombre: "+rs.getString(2));
				System.out.println("2.- Apellidos: " + rs.getString(3));
				System.out.println("3.- Dirección: " + rs.getString(4));
				System.out.println("4.- Teléfono: " + rs.getInt(5));
				System.out.println("5.- Fecha de nacimiento: " + 
						birthDate.getDayOfMonth() + "-" + birthDate.getMonthValue() +
						"-" + birthDate.getYear());
			}
			
		}else System.out.println("No se han encontrado resultados para el registro seleccionado.");
	}
	
	//Modifica un registro de cliente
	private static void modifyCliente(ManejadorBaseDatos manejador) throws ClassNotFoundException, SQLException {
		
		boolean exitUpdate = false;
		String SQL_UPDATE_CLIENTE;
		
		showClientes(manejador);
		System.out.println("Seleccione el número del cliente que desea modificar: ");
		int number = sc.nextInt();
		sc.nextLine();
		
		do {
			System.out.println("Datos actuales de cliente seleccionado: ");
			modifyClienteAux(manejador, number);
			System.out.println("Selecciona el registro que desea modificar (0 para terminar): ");
			String option = sc.nextLine();
			
			switch (option) {
			case "0": exitUpdate = true; break;
			
			case "1"://Nombres
				String newName = validateString("Nombres: ", NAMES_MAX_LENGTH);
				SQL_UPDATE_CLIENTE = "UPDATE clientes SET nombres = '"+newName+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_CLIENTE);
				System.out.println("Nombre "+newName+" actualizado.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "2": //Apellidos 
				String newSurnames = validateString("Apellidos: ", SURNAMES_MAX_LENGTH);
				SQL_UPDATE_CLIENTE = "UPDATE clientes SET apellidos = '"+newSurnames+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_CLIENTE);
				System.out.println("Apellidos "+newSurnames+" actualizado.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "3": //Dirección
				String newAddress = validateString("Dirección: ", ADDRESS_MAX_LENGTH);
				SQL_UPDATE_CLIENTE = "UPDATE clientes SET direccion = '"+newAddress+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_CLIENTE);
				System.out.println("Dirección "+newAddress+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "4": //Télefono
				String newPhoneNum= validatePhoneNumber("Teléfono: ");
				SQL_UPDATE_CLIENTE = "UPDATE clientes SET telefono = "+Integer.parseInt(newPhoneNum)+" WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_CLIENTE);
				System.out.println("Teléfono "+newPhoneNum+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "5": //Fecha nacimiento
				LocalDate newBirthDate = validateLocalDate("Fecha de nacimiento (dd-MM-yyyy): ");
				SQL_UPDATE_CLIENTE = "UPDATE clientes SET fecha_nacim = date('"+newBirthDate.toString()+"') WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_CLIENTE);
				System.out.println("Fecha de nacimiento "
						+newBirthDate.getDayOfMonth()+"-"
						+newBirthDate.getMonthValue()+"-"
						+newBirthDate.getYear()+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
				
			case "6": //Nº de cuenta 
				System.out.print("Número de cuenta: ");
				String newBankAccount = validateString("Número de cuenta: ",BANK_ACCOUNT_MAX_LENGTH);
				SQL_UPDATE_CLIENTE = "UPDATE clientes SET nro_cuenta = '"+newBankAccount+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_CLIENTE);
				System.out.println("Nº de cuenta "+newBankAccount+" actualizada");
				if(!validateIBAN(newBankAccount))
				{
					System.out.println("El número de cuenta no ha sido verificado.");
					System.out.println("El estado actual de cliente es: "+CLIENT_STATE_PENDING);
					SQL_UPDATE_CLIENTE = "UPDATE clientes SET estado = '"+CLIENT_STATE_PENDING+"' WHERE numero ="+number+";";
					manejador.update(SQL_UPDATE_CLIENTE);
				}
				else SQL_UPDATE_CLIENTE = "UPDATE clientes SET estado = '"+CLIENT_STATE_ACTIVE+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_CLIENTE);
				System.out.println("Cuenta verificada. Estado del cliente: "+CLIENT_STATE_ACTIVE);
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
		
				
			case "7" ://Tipo cliente 
				String clientType;
				do
				{
					System.out.println("Tipo de cliente:");
					System.out.println("1. Normal");
					System.out.println("2. Premium");
					System.out.print("Elige una opción: ");
					clientType = sc.nextLine();
					
					switch (clientType) {
						case "1"://Normal 
							SQL_UPDATE_CLIENTE = "UPDATE clientes SET tipo_cliente = '"+NORMAL_CLIENT_TYPE+"' WHERE numero ="+number+";";
							manejador.update(SQL_UPDATE_CLIENTE);
							System.out.println("Actualizado tipo cliente a: "+NORMAL_CLIENT_TYPE);
							break;
						case "2": //Premium 
							SQL_UPDATE_CLIENTE = "UPDATE clientes SET tipo_cliente = '"+PREMIUM_CLIENT_TYPE+"' WHERE numero ="+number+";";
							manejador.update(SQL_UPDATE_CLIENTE);
							System.out.println("Actualizado tipo cliente a: "+PREMIUM_CLIENT_TYPE);
							break;
						default: 
							System.out.println("Opción incorrecta, debe seleccionar cualquiera de los dos tipos."); 
							break;
					}
				
				} while(!clientType.equals("1") && !clientType.equals("2"));
				
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
			
			default:
				System.out.println("Opción no permitida. Seleccione el dato que desea modificar.");
				exitUpdate = false;
				break;
			}
			
		}while(!exitUpdate);
	}
	
	//Muestra los datos de un cliente para ser editado por el usuario
	private static void modifyClienteAux(ManejadorBaseDatos manejador, int number) throws ClassNotFoundException, SQLException  {
		
		final String SQL_SELECT_CLIENTE = "SELECT * FROM clientes WHERE numero = "+number+";";
		ResultSet rs = manejador.select(SQL_SELECT_CLIENTE);
		modifyPersonaAux(manejador, number);
		if(rs.isBeforeFirst()) {
			while(rs.next()) {
				System.out.println("6.- Nº cuenta: "+rs.getString(7));
				System.out.println("7.- Tipo cliente: "+rs.getString(9));
				System.out.println("    Estado: "+rs.getString(8));
				System.out.println();
			}
		}else System.out.println("No se han encontrado registros del cliente seleccionado.");
	}

	//Modifica un registro de funcionario
	private static void modifyFuncionario(ManejadorBaseDatos manejador) throws ClassNotFoundException, SQLException {
		
		boolean exitUpdate = false;
		String SQL_UPDATE_FUNCIONARIO;
		
		showFuncionarios(manejador);
		System.out.println("Seleccione el número de funcionario que desea modificar: ");
		int number = sc.nextInt();
		sc.nextLine();
		
		do {
			System.out.println("Datos actuales de funcionario seleccionado: ");
			modifyFuncionarioAux(manejador, number);
			System.out.println("Selecciona el registro que desea modificar (0 para terminar): ");
			String option = sc.nextLine();
			
			switch (option) {
			case "0": exitUpdate = true; break;
			
			case "1"://Nombres
				String newName = validateString("Nombres: ", NAMES_MAX_LENGTH);
				SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET nombres = '"+newName+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_FUNCIONARIO);
				System.out.println("Nombre "+newName+" actualizado.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "2": //Apellidos 
				String newSurnames = validateString("Apellidos: ", SURNAMES_MAX_LENGTH);
				SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET apellidos = '"+newSurnames+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_FUNCIONARIO);
				System.out.println("Apellidos "+newSurnames+" actualizado.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "3": //Dirección
				String newAddress = validateString("Dirección: ", ADDRESS_MAX_LENGTH);
				SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET direccion = '"+newAddress+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_FUNCIONARIO);
				System.out.println("Dirección "+newAddress+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "4": //Télefono
				String newPhoneNum= validatePhoneNumber("Teléfono: ");
				SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET telefono = "+Integer.parseInt(newPhoneNum)+" WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_FUNCIONARIO);
				System.out.println("Teléfono "+newPhoneNum+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "5": //Fecha nacimiento
				LocalDate newBirthDate = validateLocalDate("Fecha de nacimiento (dd-MM-yyyy): ");
				SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET fecha_nacim = date('"+newBirthDate.toString()+"') WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_FUNCIONARIO);
				System.out.println("Fecha de nacimiento "
						+newBirthDate.getDayOfMonth()+"-"
						+newBirthDate.getMonthValue()+"-"
						+newBirthDate.getYear()+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
			
				
			case"6"://Cargo
				boolean groupOk = false;
				String groupOption;
				String newCodeGroup;
				
				do {
					
					System.out.println("Seleccione el grupo:");
					System.out.println("1. A1");
					System.out.println("2. A2");
					System.out.println("3. C1");
					System.out.println("4. C2");
					System.out.println("5. AP");
					groupOption = sc.nextLine();
					
					switch (groupOption) {
					case "1":
						System.out.println("Grupo seleccionado:  A1");
						System.out.print("Codigo del cuerpo (5 caracteres): ");
						newCodeGroup = validateString("Codigo del cuerpo (5 caracteres): ",CODE_GROUP_MAX_LENGTH);
						SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET cargo = ('A1','"+newCodeGroup+"') WHERE numero ="+number+";";
						System.out.println("Cargo actualizado: Grupo A1, Código del cuerpo: "+newCodeGroup);
						exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
						groupOk = true; 
						break;
						
					case "2":
						System.out.println("Grupo seleccionado:  A2");
						System.out.print("Codigo del cuerpo (5 caracteres): ");
						newCodeGroup = validateString("Codigo del cuerpo (5 caracteres): ",CODE_GROUP_MAX_LENGTH);
						SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET cargo = ('A2','"+newCodeGroup+"') WHERE numero ="+number+";";
						System.out.println("Cargo actualizado: Grupo A2, Código del cuerpo: "+newCodeGroup);
						exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
						groupOk = true; 
						break;
						
					case "3":
						System.out.println("Grupo seleccionado:  C1");
						System.out.print("Codigo del cuerpo (5 caracteres): ");
						newCodeGroup = validateString("Codigo del cuerpo (5 caracteres): ",CODE_GROUP_MAX_LENGTH);
						SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET cargo = ('C1','"+newCodeGroup+"') WHERE numero ="+number+";";
						System.out.println("Cargo actualizado: Grupo C1, Código del cuerpo: "+newCodeGroup);
						exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
						groupOk = true; 
						break;
						
					case "4":
						System.out.println("Grupo seleccionado:  C2");
						System.out.print("Codigo del cuerpo (5 caracteres): ");
						newCodeGroup = validateString("Codigo del cuerpo (5 caracteres): ",CODE_GROUP_MAX_LENGTH);
						SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET cargo = ('C2','"+newCodeGroup+"') WHERE numero ="+number+";";
						System.out.println("Cargo actualizado: Grupo C2, Código del cuerpo: "+newCodeGroup);
						exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
						groupOk = true; 
						break;
						
					case "5":
						System.out.println("Grupo seleccionado:  AP");
						System.out.print("Codigo del cuerpo (5 caracteres): ");
						newCodeGroup = validateString("Codigo del cuerpo (5 caracteres): ",CODE_GROUP_MAX_LENGTH);
						SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET cargo = ('AP','"+newCodeGroup+"') WHERE numero ="+number+";";
						System.out.println("Cargo actualizado: Grupo AP, Código del cuerpo: "+newCodeGroup);
						exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
						groupOk = true; 
						break;

					default:
						System.out.println("Grupo incorrecto. Debe seleccionar un grupo válido.");
						groupOk = false;
						break;
					}
				} while (!groupOk);
				
				
				break;
				
			case"7": //Fecha ingreso
				LocalDate addmisionDate = validateLocalDate("Fecha de fecha_ingreso (dd-MM-yyyy): ");
				SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET fecha_ingreso = date('"+addmisionDate+"') WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_FUNCIONARIO);
				System.out.println("Fecha de nacimiento "
						+addmisionDate.getDayOfMonth()+"-"
						+addmisionDate.getMonthValue()+"-"
						+addmisionDate.getYear()+" actualizada.");
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			case "8": //Departamento
				String newDepartment = validateString("Departamento: ", DEPARTMENT_MAX_LENGTH);
				SQL_UPDATE_FUNCIONARIO = "UPDATE funcionarios SET departamento = '"+newDepartment+"' WHERE numero ="+number+";";
				manejador.update(SQL_UPDATE_FUNCIONARIO);
				System.out.println("Departamento actualizado: "+newDepartment);
				exitUpdate = askExitUpdate("¿Desea continuar editando? (si/no)");
				break;
				
			default:
				System.out.println("Opción no permitida. Seleccione el dato que desea modificar.");
				exitUpdate = false;
				break;
			}
		}while(!exitUpdate);
					
	
	}

	//Muestra los datos de un funcionario para ser editado por el usuario
	private static void modifyFuncionarioAux(ManejadorBaseDatos manejador, int number) throws ClassNotFoundException, SQLException{
		
		final String SQL_SELECT_FUNCIONARIO = "SELECT * FROM funcionarios WHERE numero = "+number+";";
		ResultSet rs = manejador.select(SQL_SELECT_FUNCIONARIO);
		modifyPersonaAux(manejador, number);
		if(rs.isBeforeFirst()) {
			while(rs.next()) {
				LocalDate addmisionDate = rs.getDate(8).toLocalDate();
				System.out.println("6.- Cargo: "+rs.getString(7));
				System.out.println("7.- Fecha de ingreso: "+
						addmisionDate.getDayOfMonth()+"-"
						+addmisionDate.getMonthValue()+"-"
						+addmisionDate.getYear());
				System.out.println("8.- Departamento: "+rs.getString(9));
				System.out.println();
			}
			
		}else System.out.println("No se han encontrado registros del funcionario seleccionado.");
	}

	//Muestra todos los registros de personas
	private static void showPersonas(ManejadorBaseDatos manejador) throws SQLException, ClassNotFoundException {
		
		final String SQL_SELECT_PERSONAS = "SELECT * FROM ONLY personas";
		ResultSet rs = manejador.select(SQL_SELECT_PERSONAS);
		personHeader();
		if(rs.isBeforeFirst()) {
			while(rs.next()) {
				LocalDate d = rs.getDate(6).toLocalDate();
				System.out.println(rs.getString(1)+"    "+rs.getString(2)+
						"        "+rs.getString(3)
						+"   "+rs.getString(4)+
						"   "+rs.getString(5)+
						"    "+d.getDayOfMonth()+"-"+d.getMonthValue()+"-"+d.getYear());
				
			}
			System.out.println();
		}else 
			System.out.println("No se han encontrado registros de Personas.");
			
		

	}
	
	//Muestra todos los registros de clientes
	private static void showClientes(ManejadorBaseDatos manejador) throws ClassNotFoundException, SQLException {
		
		final String SQL_SELECT_CLIENTES = "SELECT * FROM clientes";
		ResultSet rs = manejador.select(SQL_SELECT_CLIENTES);
		clientHeader();
		if(rs.isBeforeFirst())
		{
			while(rs.next()) {
				LocalDate d = rs.getDate(6).toLocalDate();
				System.out.println(rs.getString(1)+"    "+rs.getString(2)+
						"         "+rs.getString(3)
						+"   "+rs.getString(4)+
						"      "+rs.getString(5)+
						"      "+d.getDayOfMonth()+"-"+d.getMonthValue()+"-"+d.getYear()+
						"        "+rs.getString(7)+
						"    "+rs.getString(8)+
						"    "+rs.getString(9));
			}
			System.out.println();
		}
		else
			System.out.println("No se han encontrado registros de Clientes");
	}
	
	//Muestra todos los registros de funcionarios
	private static void showFuncionarios(ManejadorBaseDatos manejador) throws ClassNotFoundException, SQLException {
		
		final String SQL_SELECT_FUNCIONARIOS = "SELECT * FROM funcionarios";
		ResultSet rs = manejador.select(SQL_SELECT_FUNCIONARIOS);
		funcionarioHeader();
		if(rs.isBeforeFirst()) {
			
			while(rs.next()) {
				LocalDate birthDate = rs.getDate(6).toLocalDate();
				LocalDate admissionDate = rs.getDate(8).toLocalDate();
				System.out.println(rs.getString(1)+"    "+rs.getString(2)+
						"         "+rs.getString(3)
						+"   "+rs.getString(4)+
						"    "+rs.getString(5)+
						"      "+birthDate.getDayOfMonth()+"-"+birthDate.getMonthValue()+"-"+birthDate.getYear()+
						"        "+rs.getString(7)+
						"    "+rs.getString(9)+
						"    "+admissionDate.getDayOfMonth()+"-"
							  +admissionDate.getMonthValue()+"-"
							  +admissionDate.getYear());
			}
			System.out.println();
		}else 
			System.out.println("No se han encontrado registros de Clientes");
		
		
	}
	
	

	
	
	//----------------------------------- FUNCIONES AUXILIARES ----------------------------------------------//
	
	//Valida que una cadena de texto no esté vacía
	private static String validateString(String msg, int stringLength) {
		String result;
		System.out.println(msg);
		do {
			result = sc.nextLine();
			if(result.isEmpty() || result.isBlank()) {
				System.out.println("El campo no puede quedar vacío");
				System.out.println(msg);
			}
			
			if(result.length() > stringLength) {
				System.out.println("El dato a introducir no puede contener más de: "+stringLength+" caracteres.");
				System.out.println(msg);
			}
		} while (result.isEmpty() || result.isBlank() || result.length() > stringLength);
		
		return result;
	}
	
	//Valida un número de teléfono
	private static String validatePhoneNumber(String msg) {
		
		boolean numOk = false;
		int result = 0;
		do {
			try {
				System.out.println(msg);
				result = sc.nextInt();
				if(result < MIN_PHONE_NUMBER || result > MAX_PHONE_NUMBER) {
					System.out.println("El número de teléfono no es correcto");
					numOk = false;	
				}
				else numOk = true;
				
			}catch (NumberFormatException e) {
				System.out.println("No ha introducido un número");
				numOk = false;
			}
		} while (!numOk);
		sc.nextLine();
		return String.valueOf(result);
	}
	
	//Valida un fecha con formato dd-MM-yyyy
	private static LocalDate validateLocalDate(String msg){
    	
    	LocalDate date = LocalDate.now();
    	boolean dateOK = false;
    	String dateString;
    	System.out.println(msg);
    	do {

    		dateString = sc.nextLine();
	    	try {
				date = LocalDate.parse(dateString, formatter);
				dateOK = true;
			} catch (DateTimeParseException e) {
				System.out.println("Debe introducir un formato correcto: (dd-MM-yyyy).");
				System.out.println(msg);
				dateOK = false;
			}
    	}while(!dateOK);
    	return date;
    }
	
	//Valida un IBAN
	private static boolean validateIBAN(String cuenta) {

		boolean result = false;
		int i = 2;
		int ASCIIcharacter = 0;
		int resto = 0;
		int dc = 0;
		String cadenaDC = "";
		BigInteger cuentaNumero = new BigInteger("0");
		BigInteger modo = new BigInteger("97");
		
		if(cuenta.length() == 24 && cuenta.substring(0,1).toUpperCase().equals("E")
			&& cuenta.substring(1,2).toUpperCase().equals("S")) {
			
			do {
				ASCIIcharacter = cuenta.codePointAt(i);
				result = (ASCIIcharacter > 47 && ASCIIcharacter < 58);
				i++;
				
			} while (i < cuenta.length() && result);
			
			if(result) {
				cuentaNumero = new BigInteger(cuenta.substring(4,24)+"142800");
				resto = cuentaNumero.mod(modo).intValue();
				dc = 98 - resto;
				cadenaDC = String.valueOf(dc);
			}
			
			if(dc < 10) 
				cadenaDC = "0" + cadenaDC;
			
			//Comparamos los caracteres 2 y 3 de la cuenta (digitos de control) con cadenaDC
			if(cuenta.substring(2,4).equals(cadenaDC))
				result = true;
			else 
				result = false;
		}
		
		return result;
	}
	
	//Pide confirmación al usuario para salir del modo editar
	private static boolean askExitUpdate(String msg) {
		boolean exit = false;
		String answer;
		
		System.out.println(msg);
		answer = sc.nextLine();
		if(answer.equals("si"))
			exit = false;
		else if(answer.equals("no"))
			exit = true;
		else 
			System.out.println("Debe especificar \"si\" para salir o \"no\" para seguir editando.");
		
		return exit;
	}
	
	//Cabecera para los datos de tipo persona
	private static void personHeader() {
		System.out.println();
		System.out.println("ID   Nombre       Apellidos       Dirección         Teléfono      FecNacimiento");
		System.out.println("--------------------------------------------------------------------------------");
	}
	
	//Cabecera para los datos de tipo cliente
	private static void clientHeader() {

		System.out.println();
		System.out.println("ID   Nombre       Apellidos       Dirección         Teléfono      FecNacimiento       "
				+ "NºCuenta                  Estado     Tipo Cliente");
		System.out.println("-------------------------------------------------------------------------------------"
				+ "----------------------------------------------");
	}
	
	//Cabecera para los datos de tipo funcionario
	private static void funcionarioHeader() {
		System.out.println();
		System.out.println("ID   Nombre       Apellidos       Dirección         Teléfono      FecNacimiento    "
				+ "Cargo        Departamento     Fecha Ingreso");
		System.out.println("-------------------------------------------------------------------------------------"
				+ "-----------------------------------------");
	}
}
