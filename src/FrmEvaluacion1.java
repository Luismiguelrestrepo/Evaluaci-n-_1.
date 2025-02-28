import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmEvaluacion1 extends JFrame {
    private int[] denominaciones = {50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    private int[] existencias = new int[denominaciones.length];

    private JComboBox cmbDenominacion;
    private JTextField txtActualizarExistencia, txtValorAdevolver;
    private JTable tblTablaDistribucion;
    private DefaultTableModel formadeTabla;

    public FrmEvaluacion1() {
        setSize(600, 400);
        setTitle("Caja registradora");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblDenominacion = new JLabel("Denominación:");
        lblDenominacion.setBounds(20, 10, 100, 25);
        getContentPane().add(lblDenominacion);

        cmbDenominacion = new JComboBox();
        cmbDenominacion.setBounds(130, 10, 100, 25);
        for (int den : denominaciones) {
            cmbDenominacion.addItem(String.valueOf(den));
        }
        getContentPane().add(cmbDenominacion);

        JButton btnActualizarExistencia = new JButton("Actualizar Existencia");
        btnActualizarExistencia.setBounds(20, 45, 180, 25);
        getContentPane().add(btnActualizarExistencia);

        txtActualizarExistencia = new JTextField();
        txtActualizarExistencia.setBounds(210, 45, 50, 25);
        getContentPane().add(txtActualizarExistencia);

        JLabel lblValorAdevolver = new JLabel("Valor a devolver:");
        lblValorAdevolver.setBounds(20, 80, 100, 25);
        getContentPane().add(lblValorAdevolver);

        txtValorAdevolver = new JTextField();
        txtValorAdevolver.setBounds(140, 80, 100, 25);
        getContentPane().add(txtValorAdevolver);

        JButton btnDevolver = new JButton("Devolver");
        btnDevolver.setBounds(250, 80, 100, 25);
        getContentPane().add(btnDevolver);

        String[] columnas = {"Cantidad", "Presentacion", "Denominacion"};
        formadeTabla = new DefaultTableModel(columnas, 0);
        tblTablaDistribucion = new JTable(formadeTabla);
        JScrollPane spTabla = new JScrollPane(tblTablaDistribucion);
        spTabla.setBounds(20, 120, 550, 200);
        getContentPane().add(spTabla);

        btnActualizarExistencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarExistencias();
            }
        });

        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularDevolucion();
            }
        });
    }

    private void actualizarExistencias() {
        try {
            int denominacionSeleccionada = Integer.parseInt((String) cmbDenominacion.getSelectedItem());
            int cantidad = Integer.parseInt(txtActualizarExistencia.getText());

            for (int i = 0; i < denominaciones.length; i++) {
                if (denominaciones[i] == denominacionSeleccionada) {
                    existencias[i] = cantidad;
                    JOptionPane.showMessageDialog(null, "Existencia actualizada.");

                    txtActualizarExistencia.setText("");
                    
                    return;
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
        }
    }

    private void calcularDevolucion() {
        try {
            int monto = Integer.parseInt(txtValorAdevolver.getText());
            int[] cantidadUsada = new int[denominaciones.length];
            
            formadeTabla.setRowCount(0);

            for (int i = 0; i < denominaciones.length; i++) {
                if (monto >= denominaciones[i] && existencias[i] > 0) {
                    int cantidad = Math.min(monto / denominaciones[i], existencias[i]);
                    cantidadUsada[i] = cantidad;
                    monto -= cantidad * denominaciones[i];
                    existencias[i] -= cantidad;
                }
            }

            for (int i = 0; i < denominaciones.length; i++) {
                if (cantidadUsada[i] > 0) {
                    formadeTabla.addRow(new Object[]{cantidadUsada[i], (denominaciones[i] >= 1000) ? "Billete" : "Moneda", denominaciones[i]});

                }
            }

            if (monto > 0) {
                JOptionPane.showMessageDialog(null, "No hay suficiente dinero para devolver el monto exacto.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un monto válido.");
        }
    }
}
