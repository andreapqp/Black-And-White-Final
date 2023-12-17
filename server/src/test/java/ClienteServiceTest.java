import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.cliente.Cliente;
import com.cliente.ClienteRepository;
import com.cliente.ClienteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService clienteService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCliente() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId("1");
        cliente.setNome("Mario Rossi");

        when(repository.save(cliente)).thenReturn(cliente);

        // Act
        Cliente savedCliente = clienteService.addCliente(cliente);

        // Assert
        assertNotNull(savedCliente);
        assertEquals(cliente.getId(), savedCliente.getId());
        assertEquals(cliente.getNome(), savedCliente.getNome());

        verify(repository, times(1)).save(cliente);
    }

    @Test
    public void testGetClienteById() {
        // Arrange
        String clienteId = "1";
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        cliente.setNome("Mario Rossi");

        when(repository.findById(clienteId)).thenReturn(Optional.of(cliente));

        // Act
        Cliente retrievedCliente = clienteService.getClienteById(clienteId);

        // Assert
        assertNotNull(retrievedCliente);
        assertEquals(cliente.getId(), retrievedCliente.getId());
        assertEquals(cliente.getNome(), retrievedCliente.getNome());

        verify(repository, times(1)).findById(clienteId);
    }

    @Test
    public void testDeleteCliente_existingCliente() {
        // Arrange
        String clienteId = "1";

        when(repository.existsById(clienteId)).thenReturn(true);

        // Act
        boolean deleted = clienteService.deleteCliente(clienteId);

        // Assert
        assertTrue(deleted);
        verify(repository, times(1)).existsById(clienteId);
        verify(repository, times(1)).deleteById(clienteId);
    }

    @Test
    public void testDeleteCliente_nonExistingCliente() {
        // Arrange
        String clienteId = "1";

        when(repository.existsById(clienteId)).thenReturn(false);

        // Act
        boolean deleted = clienteService.deleteCliente(clienteId);

        // Assert
        assertFalse(deleted);
        verify(repository, times(1)).existsById(clienteId);
        verify(repository, never()).deleteById(clienteId);
    }

    @Test
    public void testGetAllClienti() {
        // Arrange
        List<Cliente> clienti = new ArrayList<>();
        clienti.add(new Cliente("1", "Mario Rossi"));
        clienti.add(new Cliente("2", "Luigi Verdi"));

        when(repository.findAll()).thenReturn(clienti);

        // Act
        List<Cliente> allClienti = clienteService.getAllClienti();

        // Assert
        assertNotNull(allClienti);
        assertEquals(clienti.size(), allClienti.size());

        verify(repository, times(1)).findAll();
    }
}
