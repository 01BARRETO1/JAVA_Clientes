package krakedev.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import krakedev.clientes.entidades.Cliente;
import krakedev.clientes.services.ServicioCliente;

/**
 * Tests unitarios para ServicioCliente verificando también el campo email.
 * - Solo se usa @Test (no @BeforeEach).
 * - JUnit 5.
 * - Se utilizan assertEquals, assertTrue, assertFalse y assertNull según aplique.
 */
public class ServicioClienteTest {

    // Prueba: crear un cliente nuevo correctamente con email.
    // Se prueba que crear devuelve el cliente (no null), que la cédula y el email coinciden,
    // y que la lista contiene exactamente 1 elemento.
    @Test
    public void crearCliente_Nuevo_DeberiaAgregarYRetornarClienteConEmail() {
        ServicioCliente servicio = new ServicioCliente();
        Cliente cliente = new Cliente("0102030405", "Carlos", "Pérez", "carlos@mail.com");

        Cliente resultado = servicio.crear(cliente);

        // resultado no debe ser null
        assertTrue(resultado != null);
        // la cédula del cliente devuelto debe coincidir con la esperada
        assertEquals("0102030405", resultado.getCedula());
        // el email debe haberse guardado correctamente
        assertEquals("carlos@mail.com", resultado.getEmail());
        // la lista debe tener exactamente 1 cliente
        assertEquals(1, servicio.listar().size());
    }

    // Prueba: intentar crear un cliente con cédula duplicada.
    // Se espera que el primer crear agregue y el segundo con la misma cédula retorne null,
    // además la lista no debe aumentar (permanece con 1 elemento).
    @Test
    public void crearCliente_Duplicado_DeberiaRetornarNullYNoDuplicar() {
        ServicioCliente servicio = new ServicioCliente();
        Cliente cliente1 = new Cliente("0203040506", "Ana", "Gómez", "ana@mail.com");
        Cliente cliente2 = new Cliente("0203040506", "Ana", "Gómez", "ana@mail.com");

        Cliente r1 = servicio.crear(cliente1);
        Cliente r2 = servicio.crear(cliente2);

        // primer crear debe agregar (no null)
        assertTrue(r1 != null);
        // segundo crear con misma cédula debe retornar null
        assertNull(r2);
        // la lista debe contener solo 1 cliente
        assertEquals(1, servicio.listar().size());
    }

    // Prueba: buscar un cliente existente por cédula y verificar email.
    // Se espera que buscarPorCedula devuelva el cliente correcto (no null) y con el email esperado.
    @Test
    public void buscarPorCedula_Existente_DeberiaRetornarClienteConEmail() {
        ServicioCliente servicio = new ServicioCliente();
        Cliente cliente = new Cliente("0304050607", "Luis", "Martínez", "luis@mail.com");
        servicio.crear(cliente);

        Cliente encontrado = servicio.buscarPorCedula("0304050607");

        // debe encontrar el cliente (no null)
        assertTrue(encontrado != null);
        // la cédula encontrada debe coincidir
        assertEquals("0304050607", encontrado.getCedula());
        // el email debe coincidir con el guardado
        assertEquals("luis@mail.com", encontrado.getEmail());
    }

    // Prueba: buscar un cliente que no existe.
    // Se espera que buscarPorCedula devuelva null.
    @Test
    public void buscarPorCedula_NoExistente_DeberiaRetornarNull() {
        ServicioCliente servicio = new ServicioCliente();

        Cliente encontrado = servicio.buscarPorCedula("9999999999");

        // no debe encontrar nada
        assertNull(encontrado);
    }

    // Prueba: listar clientes conserva el email de cada cliente.
    // Se agrega 2 clientes con emails distintos y se verifica que la lista tenga tamaño 2
    // y que los emails de cada posición sean los esperados.
    @Test
    public void listar_AfterAgregarDosClientes_DeberiaRetornarDosElementosConEmails() {
        ServicioCliente servicio = new ServicioCliente();
        Cliente c1 = new Cliente("1111111111", "Marta", "Lopez", "marta@mail.com");
        Cliente c2 = new Cliente("2222222222", "Pedro", "Suárez", "pedro@mail.com");
        servicio.crear(c1);
        servicio.crear(c2);

        // la lista debe tener 2 elementos
        assertEquals(2, servicio.listar().size());
        // verificar emails de cada cliente en la lista
        assertEquals("marta@mail.com", servicio.listar().get(0).getEmail());
        assertEquals("pedro@mail.com", servicio.listar().get(1).getEmail());
    }

    // Prueba: actualizar un cliente existente incluyendo email.
    // Se espera que actualizar devuelva el cliente actualizado (no null) y que nombre, apellido y email cambien.
    @Test
    public void actualizar_Existente_DeberiaActualizarNombreApellidoYEmail() {
        ServicioCliente servicio = new ServicioCliente();
        Cliente original = new Cliente("4444444444", "OldName", "OldLast", "old@mail.com");
        servicio.crear(original);

        Cliente actualizado = new Cliente("4444444444", "NewName", "NewLast", "new@mail.com");
        Cliente resultado = servicio.actualizar("4444444444", actualizado);

        // resultado no debe ser null
        assertTrue(resultado != null);
        // los campos deben haberse actualizado
        assertEquals("NewName", resultado.getNombre());
        assertEquals("NewLast", resultado.getApellido());
        assertEquals("new@mail.com", resultado.getEmail());
    }

    // Prueba: actualizar un cliente que no existe.
    // Se espera que actualizar devuelva null.
    @Test
    public void actualizar_NoExistente_DeberiaRetornarNull() {
        ServicioCliente servicio = new ServicioCliente();
        Cliente actualizado = new Cliente("5555555555", "Nombre", "Apellido", "correo@mail.com");

        Cliente resultado = servicio.actualizar("5555555555", actualizado);

        // como no existe, debe retornar null
        assertNull(resultado);
    }

    // Prueba: eliminar un cliente existente.
    // Se espera que eliminar devuelva true y que la lista disminuya (quede vacía).
    @Test
    public void eliminar_Existente_DeberiaRetornarTrueYRemoverCliente() {
        ServicioCliente servicio = new ServicioCliente();
        Cliente cliente = new Cliente("6666666666", "Elena", "Ríos", "elena@mail.com");
        servicio.crear(cliente);

        boolean eliminado = servicio.eliminar("6666666666");

        // debe devolver true al eliminar
        assertTrue(eliminado);
        // la lista debe quedar vacía
        assertEquals(0, servicio.listar().size());
    }

    // Prueba: eliminar un cliente que no existe.
    // Se espera que eliminar devuelva false y que la lista no cambie.
    @Test
    public void eliminar_NoExistente_DeberiaRetornarFalse() {
        ServicioCliente servicio = new ServicioCliente();
        servicio.crear(new Cliente("7777777777", "Sofia", "Mora", "sofia@mail.com"));

        boolean eliminado = servicio.eliminar("8888888888");

        // no existe la cédula, debe devolver false
        assertFalse(eliminado);
        // la lista debe seguir con 1 elemento
        assertEquals(1, servicio.listar().size());
    }
}
