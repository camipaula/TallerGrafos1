import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Grafos extends JFrame{
    private javax.swing.JPanel JPanel;
    private JCheckBox direccionadoCheckBox;
    private JCheckBox conPesoCheckBox;
    private JButton crearButton;
    private JButton quemarButton;
    private JTextField txtVertice;
    private JButton insertarButton;
    private JComboBox VeriticeInicioCBox;
    private JComboBox verticeFinalCBox;
    private JTextField txtPeso;
    private JButton insertarButton1;
    private JButton mostrarGrafoButton;
    private JComboBox VInicialCBox;
    private JButton DFSButton;
    private JButton BFSButton;
    private JButton dijkstraButton;
    private JTextArea textArea1;
    private Grafo g;

    public Grafos(String title) {

        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(JPanel);
        this.pack();


        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean seleccionadoDireccion,seleccionadoPeso;
                seleccionadoPeso=conPesoCheckBox.isSelected();
                if(!seleccionadoPeso){
                    txtPeso.setText("1");
                    txtPeso.setEnabled(false);
                }else{
                    txtPeso.setText("");
                    txtPeso.setEnabled(true);
                }
                seleccionadoDireccion=direccionadoCheckBox.isSelected();
                g=new Grafo(seleccionadoDireccion,seleccionadoPeso);
                JOptionPane.showMessageDialog(null, "Grafo creado con éxito");
            }
        });
        quemarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!txtVertice.getText().isBlank()){
                    if(g.getVertexByValue(txtVertice.getText())==null){
                        g.addVertice(txtVertice.getText());
                        cargarComboBox();
                        JOptionPane.showMessageDialog(null, "Vertice añadido con éxito");
                        System.out.printf("Tamaño al imprimir: "+g.getVertices().size());
                    }else{
                        JOptionPane.showMessageDialog(null, "Ya hay un vertice con ese nombre,pruebe con otro");
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Llene correctamente el campo de vertice");
                    textArea1.setText("No se añadio el vertice, reintente...");
                }
            }
        });


        insertarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(g.getVertexByValue(VeriticeInicioCBox.getSelectedItem().toString()).aristaUnica(verticeFinalCBox.getSelectedItem().toString())){
                    g.addArista(VeriticeInicioCBox.getSelectedItem().toString(),verticeFinalCBox.getSelectedItem().toString(),Integer.parseInt(txtPeso.getText()));
                    JOptionPane.showMessageDialog(null, "Arista agregada con éxito");
                }else{
                    JOptionPane.showMessageDialog(null, "Esta arista ya existe");
                }
            }
        });
        mostrarGrafoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
                for (int i = 0; i < g.getVertices().size(); i++) {
                    textArea1.append(g.getVertices().get(i).imprimir(g.isConPesos())+"\n");
                }
            }
        });
        DFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Vertice> vertices=new ArrayList<Vertice>();
                vertices.add(g.getVertexByValue(VInicialCBox.getSelectedItem().toString()));
                textArea1.setText(g.depthFirstTraversal(g.getVertexByValue(VInicialCBox.getSelectedItem().toString()),vertices));

            }
        });
        BFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(g.breadthFirstTraversal(g.getVertexByValue(VInicialCBox.getSelectedItem().toString())));
            }
        });
    }
    private void cargarComboBox(){
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> comboBoxModel2 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> comboBoxModel3 = new DefaultComboBoxModel<>();
        VeriticeInicioCBox.setModel(comboBoxModel);
        verticeFinalCBox.setModel(comboBoxModel2);
        VInicialCBox.setModel(comboBoxModel3);
        for (Vertice vertice : g.getVertices()) {
            String data = vertice.getDato();
            comboBoxModel.addElement(data);
            comboBoxModel2.addElement(data);
            comboBoxModel3.addElement(data);
        }
    }
}
