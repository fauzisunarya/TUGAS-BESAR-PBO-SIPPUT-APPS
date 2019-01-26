/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.siput.library.view;

import gov.siput.library.controller.DataBukuController;
import gov.siput.library.controller.DataPetugasController;
import gov.siput.library.controller.DataSiswaController;
import gov.siput.library.database.siputDatabase;
import gov.siput.library.entity.DataBuku;
import gov.siput.library.entity.DataPetugas;
import gov.siput.library.entity.DataSiswa;
import gov.siput.library.error.DataBukuException;
import gov.siput.library.error.DataPetugasException;
import gov.siput.library.error.DataSiswaException;
import gov.siput.library.event.DataBukuListener;
import gov.siput.library.event.DataPetugasListener;
import gov.siput.library.event.DataSiswaListener;
import gov.siput.library.model.DataBukuModel;
import gov.siput.library.model.DataPetugasModel;
import gov.siput.library.model.DataSiswaModel;
import gov.siput.library.model.TableDataBukuModel;
import gov.siput.library.model.TableDataPetugasModel;
import gov.siput.library.model.TableDataSiswaModel;
import gov.siput.library.service.DataBukuDao;
import gov.siput.library.service.DataPetugasDao;
import gov.siput.library.service.DataSiswaDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.util.Collections.list;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;

/**
 *
 * @author Administrator
 */
public class DashboardMainView extends javax.swing.JFrame implements DataBukuListener, DataSiswaListener, DataPetugasListener, ListSelectionListener{
    
    private TableDataBukuModel tablemodelbuku;
    private TableDataSiswaModel tablemodelsiswa;
    private TableDataPetugasModel tablemodelpetugas;
    private DataSiswaModel modelsiswa;
    private DataBukuModel modelbuku;
    private DataPetugasModel modelpetugas;
    private DataBukuController controllerbuku;
    private DataSiswaController controllersiswa;
    private DataPetugasController controllerpetugas;
    private Statement st;
    private Connection Con;
    private ResultSet RsBuku;
    private ResultSet RsAnggota;
    private ResultSet Rs;
    private ResultSet RsPetugas;
    private ResultSet RsJual;
    private ResultSet RsDetail;
    private ResultSet RsPeminjaman;
    private ResultSet RsUser;
    private ResultSet RsPinjam;
    public String kodepinjam,kodepetugas,namapetugas,NISN,namasiswa,kelassiswa,kode_kembali,no_pinjam,namaanggota,Tanggalpinjam,Tanggalkembali,kodebuku,judulbuku;
    public int jumlahpinjam,Keterlambatan,Denda,Hasil;
    private String Tanggal="";
    private String Sql="";
    
    public DashboardMainView() throws SQLException, DataBukuException, DataSiswaException, DataPetugasException { 
        tablemodelbuku = new TableDataBukuModel();
        
        modelbuku = new DataBukuModel();
        modelbuku.setListener(this);
        
        controllerbuku = new DataBukuController();
        controllerbuku.setModel(modelbuku);
        
        tablemodelsiswa = new TableDataSiswaModel();
        
        modelsiswa = new DataSiswaModel();
        modelsiswa.setListener(this);
        
        controllersiswa = new DataSiswaController();
        controllersiswa.setModel(modelsiswa);
        
        tablemodelpetugas = new TableDataPetugasModel();
        
        modelpetugas = new DataPetugasModel();
        modelpetugas.setListener(this);
        
        controllerpetugas = new DataPetugasController();
        controllerpetugas.setModel(modelpetugas);
        
        initComponents();
        jTable_DataBuku.getSelectionModel().addListSelectionListener(this);
        jTable_DataSiswa.getSelectionModel().addListSelectionListener(this);
        jTable_DataPetugas.getSelectionModel().addListSelectionListener(this);
        
        jTable_DataSiswa.setModel(tablemodelsiswa);
        jTable_DataBuku.setModel(tablemodelbuku);
        jTable_DataPetugas.setModel(tablemodelpetugas);
        loadDatabase();
         try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
        Koneksi();
        ChartDataBuku();
        ChartDataSiswa();
        ChartDataPeminjam();
        tampilpeminjaman();
        Waktu();
        tampilPengembalian("selcet*from pengembalian");
        kosongkanPengembalian(); 
        TgldiText();

        jTextField_TanggalPinjamPengembalian.enable(false);
        jTextField_KeterlambatanPengembalian.enable(false);
        
    }

    // ====================== Data Buku ====================== \\
    
    public JTable getjTable_DataBuku() {
        return jTable_DataBuku;
    }

    public JTextField getjTextField_JudulBuku() {
        return jTextField_JudulBuku;
    }

    public JTextField getjTextField_JumlahBuku() {
        return jTextField_JumlahBuku;
    }

    public JTextField getjTextField_KodeBuku() {
        return jTextField_KodeBuku;
    }

    public JTextField getjTextField_Penerbit() {
        return jTextField_Penerbit;
    }

    public JTextField getjTextField_Pengarang() {
        return jTextField_Pengarang;
    }

    public JTextField getjTextField_TahunTerbit() {
        return jTextField_TahunTerbit;
    }

    public JTextField getjTextField_ID() {
        return jTextField_ID;
    }

    public JTextField getjTextField_KodeRak() {
        return jTextField_KodeRak;
    }
    
    
    // ====================== Data Siswa ====================== \\

    public JTextField getjTextField_AlamatSiswa() {
        return jTextField_AlamatSiswa;
    }

    public JTextField getjTextField_IdSiswa() {
        return jTextField_IdSiswa;
    }

    public JTextField getjTextField_JenisKelamin() {
        return jTextField_JenisKelamin;
    }

    public JTextField getjTextField_Kelas() {
        return jTextField_Kelas;
    }

    public JTextField getjTextField_NamaSiswa() {
        return jTextField_NamaSiswa;
    }

    public JTextField getjTextField_NoHpSiswa() {
        return jTextField_NoHpSiswa;
    }

    public JTextField getjTextField_TanggalLahirSiswa() {
        return jTextField_TanggalLahirSiswa;
    }

    public JTextField getjTextField_TempatLahirSiswa() {
        return jTextField_TempatLahirSiswa;
    }

    public JTable getjTable_DataSiswa() {
        return jTable_DataSiswa;
    }

    public JTextField getjTextField_NISN() {
        return jTextField_NISN;
    }
    
    
    // ====================== Data Petugas ====================== \\

    public JTextField getjTextField_AlamatPetugas() {
        return jTextField_AlamatPetugas;
    }

    public JTextField getjTextField_IdPetugas() {
        return jTextField_IdPetugas;
    }

    public JTextField getjTextField_JenisKelaminPetugas() {
        return jTextField_JenisKelaminPetugas;
    }

    public JTextField getjTextField_KodePetugas() {
        return jTextField_KodePetugas;
    }

    public JTextField getjTextField_NamaPetugas() {
        return jTextField_NamaPetugas;
    }

    public JTextField getjTextField_NoHpPetugas() {
        return jTextField_NoHpPetugas;
    }

    public JTextField getjTextField_TanggalLahirPetugas() {
        return jTextField_TanggalLahirPetugas;
    }

    public JTextField getjTextField_TempatLahirPetugas() {
        return jTextField_TempatLahirPetugas;
    }

    public JTable getjTable_DataPetugas1() {
        return jTable_DataPetugas;
    }  
    
    public void ChartDataBuku(){
                try {
            String query = "select tahunterbit, jumlahbuku from buku";
            JDBCCategoryDataset dataset = new JDBCCategoryDataset(siputDatabase.getConnection(),query);
            JFreeChart chart = ChartFactory.createBarChart3D("DATA BUKU PERPUSTAKAAN", "TAHUN TERBIT", "JUMLAH BUKU", dataset, PlotOrientation.VERTICAL, false, true,true);
            
            chart.setBackgroundPaint(new Color(51,0,153));
            chart.getTitle().setPaint(Color.white);
            CategoryPlot barchart = chart.getCategoryPlot();
            
            CategoryAxis Axis = (CategoryAxis)barchart.getDomainAxis();
            Axis.setAxisLinePaint(Color.white);
            Axis.setTickLabelPaint(Color.white);
            Axis.setLabelPaint(Color.white);
            
            final NumberAxis rangeAxis = (NumberAxis) barchart.getRangeAxis();
            rangeAxis.setAxisLinePaint(Color.white);
            rangeAxis.setTickLabelPaint(Color.white);
            rangeAxis.setLabelPaint(Color.white);
            
            BarRenderer renderer=(BarRenderer)barchart.getRenderer();
            barchart.setRangeGridlinePaint(Color.white);
            barchart.setBackgroundPaint(new Color(51,0,153));
            GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.CYAN,                 
                     0.0f, 0.0f, new Color(0, 0, 64));//warna untuk barchart                
            renderer.setSeriesPaint(0, gp0);
        
            ChartPanel barPanel = new ChartPanel(chart);
            panelChart.removeAll();
            panelChart.add(barPanel,BorderLayout.CENTER);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void ChartDataSiswa(){
         try {
            String query = "select kelas, nisn from siswa";
            JDBCCategoryDataset dataset = new JDBCCategoryDataset(siputDatabase.getConnection(),query);
            JFreeChart chart = ChartFactory.createBarChart3D("DATA SISWA", "KELAS", "NISN", dataset, PlotOrientation.VERTICAL, false, true,true);
            
            chart.setBackgroundPaint(new Color(51,0,153));
            chart.getTitle().setPaint(Color.white);
            CategoryPlot barchart = chart.getCategoryPlot();
            
            CategoryAxis Axis = (CategoryAxis)barchart.getDomainAxis();
            Axis.setAxisLinePaint(Color.white);
            Axis.setTickLabelPaint(Color.white);
            Axis.setLabelPaint(Color.white);
            
            final NumberAxis rangeAxis = (NumberAxis) barchart.getRangeAxis();
            rangeAxis.setAxisLinePaint(Color.white);
            rangeAxis.setTickLabelPaint(Color.white);
            rangeAxis.setLabelPaint(Color.white);
            
            BarRenderer renderer=(BarRenderer)barchart.getRenderer();
            barchart.setRangeGridlinePaint(Color.white);
            barchart.setBackgroundPaint(new Color(51,0,153));
            GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.CYAN,                 
                     0.0f, 0.0f, new Color(0, 0, 64));//warna untuk barchart                
            renderer.setSeriesPaint(0, gp0);
        
            ChartPanel barPanel = new ChartPanel(chart);
            panelchart2.removeAll();
            panelchart2.add(barPanel,BorderLayout.CENTER);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void ChartDataPeminjam(){
        try {
            String query = "select tanggalpinjam, jumlahpinjam from pinjam";
            JDBCCategoryDataset dataset = new JDBCCategoryDataset(siputDatabase.getConnection(),query);
            JFreeChart chart = ChartFactory.createBarChart3D("DATA PEMINJAMAN BUKU", "TANGGAL PINJAM", "JUMLAH BUKU", dataset, PlotOrientation.VERTICAL, false, true,true);
            chart.setBackgroundPaint(new Color(51,0,153));
            chart.getTitle().setPaint(Color.white);
            CategoryPlot barchart = chart.getCategoryPlot();
            
            CategoryAxis axis = (CategoryAxis)barchart.getDomainAxis();
            axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            axis.setAxisLinePaint(Color.white);
            axis.setTickLabelPaint(Color.white);
            axis.setLabelPaint(Color.white);
            
            final NumberAxis rangeAxis = (NumberAxis) barchart.getRangeAxis();
            rangeAxis.setAxisLinePaint(Color.white);
            rangeAxis.setTickLabelPaint(Color.white);
            rangeAxis.setLabelPaint(Color.white);
            
            BarRenderer renderer=(BarRenderer) barchart.getRenderer();
            barchart.setRangeGridlinePaint(Color.white);
            barchart.setBackgroundPaint(new Color(51,0,153));
            GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.CYAN,                 
                     0.0f, 0.0f, new Color(0, 0, 64));//warna untuk barchart                
            renderer.setSeriesPaint(0, gp0);
        
            ChartPanel barPanel = new ChartPanel(chart);
            panelchart1.removeAll();
            panelchart1.add(barPanel,BorderLayout.CENTER);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public class FunctionKodeBuku{
       Connection con = null;
       ResultSet rs = null;
       PreparedStatement ps = null;
       public ResultSet find(String s){
           try{
           con = DriverManager.getConnection("jdbc:mysql://localhost/perpustakaan","root","");
           ps = con.prepareStatement("select * from buku where KODEBUKU = ?");
           ps.setString(1,s);
           rs = ps.executeQuery();
           }catch(Exception ex){
              JOptionPane.showMessageDialog(null, ex.getMessage());
           }
           return rs;
       }
       
   }
    
    private void Koneksi(){
        try {
        Class.forName("com.mysql.jdbc.Driver");
        Con=DriverManager.getConnection("jdbc:mysql://localhost:3306/perpustakaan","root","");
        } catch (Exception e) {
            System.out.println("Connection Failed"+e);
            JOptionPane.showMessageDialog(null, "Connection failed");
        }
     }
   
    private void prosestambah(){
        try {
            DefaultTableModel tableModel =(DefaultTableModel)jTable_PinjamAtas.getModel();
            String[]data = new String[4];
            data[0]= jTextField_KodeBukuPeminjam.getText();
            data[1]= jTextField_JudubBukuPeminjam.getText();
            data[2]= jTextField_PengarangBukuPeminjam.getText();
            data[3]= jTextField_JumlahPinjamBukuPeminjam.getText();
            tableModel.addRow(data);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error masukkan data \n"+e.getMessage());
        }
    }
    
     private void Total(){
      int jumlahBaris = jTable_PinjamAtas.getRowCount();
        int ttlpinjam = 0, jlhpinjam=0;
        int Jlhtotalpinjam;
        
        TableModel tblmodel;
        tblmodel = jTable_PinjamAtas.getModel();
        for (int i=0; i<jumlahBaris; i++){
            Jlhtotalpinjam=Integer.parseInt(tblmodel.getValueAt(i, 3).toString());
                jlhpinjam=Jlhtotalpinjam+jlhpinjam;
                }
        jTextField_TotalPinjamPeminjam.setText(String.valueOf(jlhpinjam));
     }
     
     public void Waktu(){
        Date tgl=new Date();
        jDateChooser_TanggalPinjamPeminjam.setDate(tgl);
     }
     
      private void simpandetail(){
        int jumlah_baris=jTable_PinjamAtas.getRowCount();
        if (jumlah_baris == 0) {
            JOptionPane.showMessageDialog(rootPane, "Table is empty!!");
        } else {
            try {
                int i=0;
                while(i<jumlah_baris){
                    st.executeUpdate("insert into detail_pinjam"
                            + "(KODEPINJAM,KODEBUKU,JUDULBUKU,PENGARANG,JUMLAHPINJAM)"
                            + "values('"+jTextFiel_KodePinjamPeminjam.getText()+"',"
                            + "'"+jTable_PinjamAtas.getValueAt(i,0)+"',"
                            + "'"+jTextField_JudubBukuPeminjam.getText()+"',"
                            + "'"+jTextField_PengarangBukuPeminjam.getText()+"',"
                            + "'"+jTextField_JumlahPinjamBukuPeminjam.getText()+"')");
                    i++;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan..!!"+e);
            }
        }
    }
        
  private void tampilpeminjaman(){
        DefaultTableModel grid=new DefaultTableModel();
        grid.addColumn("No");
        grid.addColumn("Kode_pinjam");
        grid.addColumn("Tanggal_Pinjam");
        grid.addColumn("Kode_Petugas");
        grid.addColumn("Nama_petugas");
        grid.addColumn("Kode_Buku");
        grid.addColumn("Judul_Buku");
        grid.addColumn("NISN");
        grid.addColumn("Nama_Siswa");
        grid.addColumn("Kelas");
        grid.addColumn("Jumlah_Pinjam");
        try {
           int i=1;
           st=Con.createStatement();
           Rs=st.executeQuery("SELECT*FROM pinjam");
           while (Rs.next()){
               grid.addRow(new Object[]{
                   (""+i++),Rs.getString(1),Rs.getString(2),Rs.getString(3),
                    Rs.getString(4),Rs.getString(5),Rs.getString(6),Rs.getString(7),
                    Rs.getString(8),Rs.getString(9),Rs.getString(10),Rs.getString(10)
               });
               jTable_Pinjam.setModel(grid);
               jTable_Pinjam.enable(true);
               jButton_InsertPinjam.requestFocus();
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Tampil"+e);
        }
    }    
  
  private void kosongkanPeminjam(){
        jTextFiel_KodePinjamPeminjam.setText("");
        jDateChooser_TanggalPinjamPeminjam.setDate(null);
        jComboBox_NISNSiswaPeminjam.setSelectedItem(null);
        jTextField_NamaSiswaPeminjam.setText("");
        jTextField_KelasSiswaPeminjam.setText("");
        jTextField_NoHpSiswaPeminjam.setText("");
        jComboBox_KodePetugasPeminjam.setSelectedItem(null);
        jTextField_NamaPetugasPinjam.setText("");
        jTextField_NohpPetugasPinjam.setText("");
        jTextField_KodeBukuPeminjam.setText("");
        jTextField_JudubBukuPeminjam.setText("");
        jTextField_PengarangBukuPeminjam.setText("");
        jTextField_JumlahPinjamBukuPeminjam.setText("");
  }
  private void hapustable() {
    DefaultTableModel model = (DefaultTableModel)jTable_PinjamAtas.getModel();

    while (model.getRowCount() > 0){
        for (int i = 0; i < model.getRowCount(); ++i){
            model.removeRow(i);
        }
    }
}  
      
       public void TgldiText(){
        Date tgl=new Date();
        SimpleDateFormat Kembali=new SimpleDateFormat("yyyy-MM-dd");
        jTextField_TanggalKembaliPengembalian.setText(Kembali.format(tgl));
//        jTextField_TanggalPinjamPengembalian.setText(Kembali.format(tgl));
       }
       
      private void kosongkanPengembalian(){
        jTextField_KodeKembaliPengembalian.setText("");
        jComboBox_KodePinjamPengembalian.setSelectedItem("Select");
        jTextField_TanggalPinjamPengembalian.setText("");
        jTextField_NamaSiswaPengembalian.setText("");
        jTextField_JumlahPinjamPengembalian.setText("");
        jTextField_KeterlambatanPengembalian.setText("");
    }
      
     private void tampilPengembalian(String sql){
        DefaultTableModel grid=new DefaultTableModel();
        grid.addColumn("No");
        grid.addColumn("Kode Pengembalian");
        grid.addColumn("Tanggal_Pengembalian");
        grid.addColumn("Kode Pinjam");
        grid.addColumn("Tanggal Pinjam");
        grid.addColumn("nama anggota");
        grid.addColumn("jumlah pinjam");
        grid.addColumn("keterlambatan");
        grid.addColumn("denda");
        try {
           int i=1;
           st=Con.createStatement();
           Rs=st.executeQuery("SELECT*FROM pengembalian");
           while (Rs.next()){
               grid.addRow(new Object[]{
                   (""+i++),Rs.getString(1),Rs.getString(2),Rs.getString(3),
                    Rs.getString(4),Rs.getString(5),Rs.getString(6),Rs.getString(7),Rs.getString(8),
               });
               jTable_Pengembalian.setModel(grid);
               jTable_Pengembalian.enable(true);
              //BtnTambah.requestFocus();
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Tampil"+e);
        }
}    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackgroundPanel = new javax.swing.JPanel();
        MenuPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        btnDatabuku = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        btnDatasiswa = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        btnDatapetugas = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        btnDatapeminjam = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        btnDatapengembalian = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel80 = new javax.swing.JLabel();
        MainPanel = new javax.swing.JPanel();
        MenuHome = new javax.swing.JPanel();
        DashboardPanel = new javax.swing.JPanel();
        panelChart = new javax.swing.JPanel();
        panelchart1 = new javax.swing.JPanel();
        panelchart2 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        DataBuku = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_DataBuku = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField_SearchBuku = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_KodeBuku = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_JudulBuku = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Pengarang = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_TahunTerbit = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField_JumlahBuku = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_Penerbit = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jTextField_KodeRak = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        DataSiswa = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_DataSiswa = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jTextField_SearchSiswa = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField_IdSiswa = new javax.swing.JTextField();
        jTextField_NISN = new javax.swing.JTextField();
        jTextField_NamaSiswa = new javax.swing.JTextField();
        jTextField_JenisKelamin = new javax.swing.JTextField();
        jTextField_TempatLahirSiswa = new javax.swing.JTextField();
        jTextField_TanggalLahirSiswa = new javax.swing.JTextField();
        jTextField_AlamatSiswa = new javax.swing.JTextField();
        jTextField_Kelas = new javax.swing.JTextField();
        jTextField_NoHpSiswa = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        DataPetugas = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_DataPetugas = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jTextField_SearchPetugas = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField_IdPetugas = new javax.swing.JTextField();
        jTextField_KodePetugas = new javax.swing.JTextField();
        jTextField_NamaPetugas = new javax.swing.JTextField();
        jTextField_JenisKelaminPetugas = new javax.swing.JTextField();
        jTextField_TempatLahirPetugas = new javax.swing.JTextField();
        jTextField_TanggalLahirPetugas = new javax.swing.JTextField();
        jTextField_AlamatPetugas = new javax.swing.JTextField();
        jTextField_NoHpPetugas = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        DataPeminjam = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTextField_NamaPetugasPinjam = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextField_NohpPetugasPinjam = new javax.swing.JTextField();
        jComboBox_KodePetugasPeminjam = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jTextField_NamaSiswaPeminjam = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jTextField_KelasSiswaPeminjam = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextField_NoHpSiswaPeminjam = new javax.swing.JTextField();
        jComboBox_NISNSiswaPeminjam = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jTextField_JudubBukuPeminjam = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField_PengarangBukuPeminjam = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jTextField_JumlahPinjamBukuPeminjam = new javax.swing.JTextField();
        jButton_TambahPinjam = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextField_KodeBukuPeminjam = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jTextFiel_KodePinjamPeminjam = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jDateChooser_TanggalPinjamPeminjam = new com.toedter.calendar.JDateChooser();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_PinjamAtas = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_Pinjam = new javax.swing.JTable();
        jLabel56 = new javax.swing.JLabel();
        jTextField_TotalPinjamPeminjam = new javax.swing.JTextField();
        jButton_InsertPinjam = new javax.swing.JButton();
        DataPengembalian = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_Pengembalian = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jTextField_KodeKembaliPengembalian = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextField_TanggalPinjamPengembalian = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField_NamaSiswaPengembalian = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTextField_JumlahPinjamPengembalian = new javax.swing.JTextField();
        jComboBox_KodePinjamPengembalian = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jTextField_KeterlambatanPengembalian = new javax.swing.JTextField();
        jTextField_DendaPengembalian = new javax.swing.JTextField();
        jButton_InsertPengembalian = new javax.swing.JButton();
        jTextField_TanggalKembaliPengembalian = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(1153, 830));

        BackgroundPanel.setBackground(new java.awt.Color(47, 0, 154));
        BackgroundPanel.setMaximumSize(new java.awt.Dimension(1300, 830));
        BackgroundPanel.setMinimumSize(new java.awt.Dimension(1300, 830));
        BackgroundPanel.setPreferredSize(new java.awt.Dimension(1300, 830));

        MenuPanel.setBackground(new java.awt.Color(60, 0, 184));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/information.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/minimize.png"))); // NOI18N
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel65MouseClicked(evt);
            }
        });

        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/magnifier.png"))); // NOI18N

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/close-button.png"))); // NOI18N
        jLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel67MouseClicked(evt);
            }
        });

        jLabel69.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("SMAN 1 HAURGEULIS");

        jLabel70.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Welcome");

        jLabel71.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(255, 255, 255));
        jLabel71.setText("Nama Petugas");

        jLabel72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/down-arrow.png"))); // NOI18N

        btnDashboard.setBackground(new java.awt.Color(60, 0, 184));
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDashboardMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDashboardMousePressed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/speedometer 14 inch.png"))); // NOI18N
        jLabel64.setText("Dashboard");
        jLabel64.setIconTextGap(10);

        javax.swing.GroupLayout btnDashboardLayout = new javax.swing.GroupLayout(btnDashboard);
        btnDashboard.setLayout(btnDashboardLayout);
        btnDashboardLayout.setHorizontalGroup(
            btnDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDashboardLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel64)
                .addContainerGap(75, Short.MAX_VALUE))
        );
        btnDashboardLayout.setVerticalGroup(
            btnDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDashboardLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel64))
        );

        btnDatabuku.setBackground(new java.awt.Color(60, 0, 184));
        btnDatabuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDatabukuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDatabukuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDatabukuMouseExited(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Buku.png"))); // NOI18N
        jLabel59.setText("Data Buku");
        jLabel59.setIconTextGap(10);

        javax.swing.GroupLayout btnDatabukuLayout = new javax.swing.GroupLayout(btnDatabuku);
        btnDatabuku.setLayout(btnDatabukuLayout);
        btnDatabukuLayout.setHorizontalGroup(
            btnDatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDatabukuLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel59)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        btnDatabukuLayout.setVerticalGroup(
            btnDatabukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDatabukuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel59))
        );

        btnDatasiswa.setBackground(new java.awt.Color(60, 0, 184));
        btnDatasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDatasiswaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDatasiswaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDatasiswaMouseExited(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(255, 255, 255));
        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Siswa 14 inch.png"))); // NOI18N
        jLabel60.setText("Data Siswa");
        jLabel60.setIconTextGap(10);

        javax.swing.GroupLayout btnDatasiswaLayout = new javax.swing.GroupLayout(btnDatasiswa);
        btnDatasiswa.setLayout(btnDatasiswaLayout);
        btnDatasiswaLayout.setHorizontalGroup(
            btnDatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDatasiswaLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel60)
                .addContainerGap(77, Short.MAX_VALUE))
        );
        btnDatasiswaLayout.setVerticalGroup(
            btnDatasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnDatapetugas.setBackground(new java.awt.Color(60, 0, 184));
        btnDatapetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDatapetugasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDatapetugasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDatapetugasMouseExited(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Petugas 14 inch.png"))); // NOI18N
        jLabel61.setText("Data Petugas");
        jLabel61.setIconTextGap(10);

        javax.swing.GroupLayout btnDatapetugasLayout = new javax.swing.GroupLayout(btnDatapetugas);
        btnDatapetugas.setLayout(btnDatapetugasLayout);
        btnDatapetugasLayout.setHorizontalGroup(
            btnDatapetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDatapetugasLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel61)
                .addContainerGap(66, Short.MAX_VALUE))
        );
        btnDatapetugasLayout.setVerticalGroup(
            btnDatapetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnDatapeminjam.setBackground(new java.awt.Color(60, 0, 184));
        btnDatapeminjam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDatapeminjamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDatapeminjamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDatapeminjamMouseExited(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Peminjam 14 inch.png"))); // NOI18N
        jLabel62.setText("Data Peminjam");
        jLabel62.setIconTextGap(10);

        javax.swing.GroupLayout btnDatapeminjamLayout = new javax.swing.GroupLayout(btnDatapeminjam);
        btnDatapeminjam.setLayout(btnDatapeminjamLayout);
        btnDatapeminjamLayout.setHorizontalGroup(
            btnDatapeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDatapeminjamLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel62)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        btnDatapeminjamLayout.setVerticalGroup(
            btnDatapeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnDatapengembalian.setBackground(new java.awt.Color(60, 0, 184));
        btnDatapengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDatapengembalianMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDatapengembalianMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDatapengembalianMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDatapengembalianMousePressed(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Pengembalian 14 inch.png"))); // NOI18N
        jLabel63.setText("Data Pengembalian");
        jLabel63.setIconTextGap(10);

        javax.swing.GroupLayout btnDatapengembalianLayout = new javax.swing.GroupLayout(btnDatapengembalian);
        btnDatapengembalian.setLayout(btnDatapengembalianLayout);
        btnDatapengembalianLayout.setHorizontalGroup(
            btnDatapengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDatapengembalianLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel63)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnDatapengembalianLayout.setVerticalGroup(
            btnDatapengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnLogout.setBackground(new java.awt.Color(60, 0, 184));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLogoutMousePressed(evt);
            }
        });

        jLabel86.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(255, 255, 255));
        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/exit 14 inch.png"))); // NOI18N
        jLabel86.setText("Logout");
        jLabel86.setIconTextGap(15);

        javax.swing.GroupLayout btnLogoutLayout = new javax.swing.GroupLayout(btnLogout);
        btnLogout.setLayout(btnLogoutLayout);
        btnLogoutLayout.setHorizontalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel86, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );
        btnLogoutLayout.setVerticalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel86, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/logosma100.png"))); // NOI18N

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel67)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(MenuPanelLayout.createSequentialGroup()
                            .addGap(172, 172, 172)
                            .addComponent(jLabel66)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel65))
                        .addGroup(MenuPanelLayout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel69)
                                    .addGroup(MenuPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel71)
                                        .addGap(56, 56, 56)))
                                .addGroup(MenuPanelLayout.createSequentialGroup()
                                    .addGap(59, 59, 59)
                                    .addComponent(jLabel70))
                                .addGroup(MenuPanelLayout.createSequentialGroup()
                                    .addGap(83, 83, 83)
                                    .addComponent(jLabel72))))
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDatasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDatapengembalian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDatapeminjam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnDatapetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel80)
                            .addGap(28, 28, 28)))
                    .addComponent(btnDatabuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel67)
                    .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel66)
                        .addComponent(jLabel65)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addComponent(jLabel80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnDatabuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDatasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnDatapetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnDatapeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(btnDatapengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        MainPanel.setBackground(new java.awt.Color(47, 0, 154));
        MainPanel.setPreferredSize(new java.awt.Dimension(1014, 853));
        MainPanel.setLayout(new java.awt.CardLayout());

        MenuHome.setBackground(new java.awt.Color(47, 0, 154));

        DashboardPanel.setBackground(new java.awt.Color(51, 0, 153));
        DashboardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        panelChart.setBackground(new java.awt.Color(255, 102, 153));
        panelChart.setFont(new java.awt.Font("Gill Sans Ultra Bold Condensed", 1, 12)); // NOI18N
        panelChart.setLayout(new java.awt.BorderLayout());

        panelchart1.setBackground(new java.awt.Color(255, 204, 255));
        panelchart1.setLayout(new java.awt.BorderLayout());

        panelchart2.setBackground(new java.awt.Color(153, 0, 255));
        panelchart2.setLayout(new java.awt.BorderLayout());

        jPanel19.setBackground(new java.awt.Color(0, 226, 214));
        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/logosma100.png"))); // NOI18N

        jLabel75.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Sistem Perpustakaan SMAN 1 HAURGEULIS");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel75)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel75)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DashboardPanelLayout = new javax.swing.GroupLayout(DashboardPanel);
        DashboardPanel.setLayout(DashboardPanelLayout);
        DashboardPanelLayout.setHorizontalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelchart2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelChart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelchart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        DashboardPanelLayout.setVerticalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelchart1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelchart2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );

        javax.swing.GroupLayout MenuHomeLayout = new javax.swing.GroupLayout(MenuHome);
        MenuHome.setLayout(MenuHomeLayout);
        MenuHomeLayout.setHorizontalGroup(
            MenuHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuHomeLayout.createSequentialGroup()
                .addComponent(DashboardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        MenuHomeLayout.setVerticalGroup(
            MenuHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DashboardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MainPanel.add(MenuHome, "card2");

        DataBuku.setBackground(new java.awt.Color(47, 0, 154));

        jPanel1.setBackground(new java.awt.Color(97, 52, 33));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Buku_1.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data Buku");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(202, 156, 94));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_DataBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable_DataBuku);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 540, 410));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/refresh-button_1.png"))); // NOI18N
        jButton1.setText("Update");
        jButton1.setIconTextGap(10);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 480, -1, 37));

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/delete.png"))); // NOI18N
        jButton14.setText("Delete");
        jButton14.setIconTextGap(10);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 480, -1, 37));

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/turn-on.png"))); // NOI18N
        jButton15.setText("Reset");
        jButton15.setIconTextGap(10);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 480, -1, 37));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/add-dock.png"))); // NOI18N
        jButton2.setText("Insert");
        jButton2.setIconTextGap(10);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 480, -1, 37));

        jTextField_SearchBuku.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTextField_SearchBuku.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_SearchBuku.setBorder(null);
        jTextField_SearchBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_SearchBukuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_SearchBukuKeyTyped(evt);
            }
        });
        jPanel2.add(jTextField_SearchBuku, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 150, -1));

        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Search.png"))); // NOI18N
        jPanel2.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 190, -1));

        jPanel17.setBackground(new java.awt.Color(70, 30, 30));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        jPanel17.setPreferredSize(new java.awt.Dimension(178, 661));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("ID                   :");

        jTextField_ID.setEditable(false);
        jTextField_ID.setBackground(new java.awt.Color(70, 30, 30));
        jTextField_ID.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_ID.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_ID.setBorder(null);
        jTextField_ID.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_ID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_IDActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Kode Buku    :");

        jTextField_KodeBuku.setBackground(new java.awt.Color(70, 30, 30));
        jTextField_KodeBuku.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_KodeBuku.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_KodeBuku.setBorder(null);
        jTextField_KodeBuku.setCaretColor(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Judul Buku    :");

        jTextField_JudulBuku.setBackground(new java.awt.Color(70, 30, 30));
        jTextField_JudulBuku.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_JudulBuku.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_JudulBuku.setBorder(null);
        jTextField_JudulBuku.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_JudulBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_JudulBukuActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Pengarang     :");

        jTextField_Pengarang.setBackground(new java.awt.Color(70, 30, 30));
        jTextField_Pengarang.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_Pengarang.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_Pengarang.setBorder(null);
        jTextField_Pengarang.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_Pengarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_PengarangActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tahun Terbit :");

        jTextField_TahunTerbit.setBackground(new java.awt.Color(70, 30, 30));
        jTextField_TahunTerbit.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_TahunTerbit.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_TahunTerbit.setBorder(null);
        jTextField_TahunTerbit.setCaretColor(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jumlah Buku :");

        jTextField_JumlahBuku.setBackground(new java.awt.Color(70, 30, 30));
        jTextField_JumlahBuku.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_JumlahBuku.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_JumlahBuku.setBorder(null);
        jTextField_JumlahBuku.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_JumlahBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_JumlahBukuActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Penerbit         :");

        jTextField_Penerbit.setBackground(new java.awt.Color(70, 30, 30));
        jTextField_Penerbit.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_Penerbit.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_Penerbit.setBorder(null);
        jTextField_Penerbit.setCaretColor(new java.awt.Color(255, 255, 255));

        jLabel73.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Kode Rak     :");

        jTextField_KodeRak.setBackground(new java.awt.Color(70, 30, 30));
        jTextField_KodeRak.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_KodeRak.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_KodeRak.setBorder(null);
        jTextField_KodeRak.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_KodeRak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_KodeRakActionPerformed(evt);
            }
        });

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Library 14 inch.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_JudulBuku)
                                    .addComponent(jTextField_Pengarang)))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_TahunTerbit))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel73)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_JumlahBuku)
                                    .addComponent(jTextField_KodeRak)))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_Penerbit))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_KodeBuku))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_ID)))))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addComponent(jLabel68)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField_KodeBuku, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField_JudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField_Pengarang, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField_TahunTerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField_Penerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_JumlahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel73)
                    .addComponent(jTextField_KodeRak, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout DataBukuLayout = new javax.swing.GroupLayout(DataBuku);
        DataBuku.setLayout(DataBukuLayout);
        DataBukuLayout.setHorizontalGroup(
            DataBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataBukuLayout.createSequentialGroup()
                .addGroup(DataBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DataBukuLayout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)))
                .addContainerGap())
        );
        DataBukuLayout.setVerticalGroup(
            DataBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataBukuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DataBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
                .addContainerGap())
        );

        MainPanel.add(DataBuku, "card3");

        DataSiswa.setBackground(new java.awt.Color(47, 0, 154));
        DataSiswa.setPreferredSize(new java.awt.Dimension(855, 831));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Siswa 60px.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Data Siswa");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(102, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jTable_DataSiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable_DataSiswa);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/refresh-button_1.png"))); // NOI18N
        jButton3.setText("Update");
        jButton3.setIconTextGap(10);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/delete.png"))); // NOI18N
        jButton16.setText("Delete");
        jButton16.setIconTextGap(10);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/turn-on.png"))); // NOI18N
        jButton17.setText("Reset");
        jButton17.setIconTextGap(10);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/add-dock.png"))); // NOI18N
        jButton18.setText("Insert");
        jButton18.setIconTextGap(10);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jTextField_SearchSiswa.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTextField_SearchSiswa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_SearchSiswa.setBorder(null);
        jTextField_SearchSiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_SearchSiswaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_SearchSiswaKeyTyped(evt);
            }
        });

        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jTextField_SearchSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel82)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 65, Short.MAX_VALUE)
                .addComponent(jButton18)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton16)
                .addGap(18, 18, 18)
                .addComponent(jButton17)
                .addGap(52, 52, 52))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTextField_SearchSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel82))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        jPanel18.setPreferredSize(new java.awt.Dimension(178, 661));

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel22.setText("Tanggal Lahir :");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel23.setText("Alamat             :");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel24.setText("Kelas                :");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel25.setText("No Hp              :");

        jLabel76.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel76.setText("NISN                :");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel19.setText("ID");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel13.setText("Nama               :");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel20.setText("Jenis Kelamin :");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel21.setText("Tempat Lahir :");

        jTextField_IdSiswa.setEditable(false);
        jTextField_IdSiswa.setBackground(new java.awt.Color(255, 255, 255));
        jTextField_IdSiswa.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_IdSiswa.setBorder(null);

        jTextField_NISN.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_NISN.setBorder(null);

        jTextField_NamaSiswa.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_NamaSiswa.setBorder(null);

        jTextField_JenisKelamin.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_JenisKelamin.setBorder(null);

        jTextField_TempatLahirSiswa.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_TempatLahirSiswa.setBorder(null);

        jTextField_TanggalLahirSiswa.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_TanggalLahirSiswa.setBorder(null);

        jTextField_AlamatSiswa.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_AlamatSiswa.setBorder(null);

        jTextField_Kelas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_Kelas.setBorder(null);

        jTextField_NoHpSiswa.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_NoHpSiswa.setBorder(null);

        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Siswa 14 inshjpg.jpg"))); // NOI18N
        jLabel78.setText("jLabel78");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel76)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20)
                    .addComponent(jLabel13)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField_NISN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(jTextField_NamaSiswa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_JenisKelamin, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_TempatLahirSiswa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_TanggalLahirSiswa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_AlamatSiswa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_Kelas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NoHpSiswa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_IdSiswa)))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField_IdSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(jTextField_NISN, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField_NamaSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField_JenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField_TempatLahirSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField_TanggalLahirSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField_AlamatSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField_Kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jTextField_NoHpSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout DataSiswaLayout = new javax.swing.GroupLayout(DataSiswa);
        DataSiswa.setLayout(DataSiswaLayout);
        DataSiswaLayout.setHorizontalGroup(
            DataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataSiswaLayout.createSequentialGroup()
                .addGroup(DataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(DataSiswaLayout.createSequentialGroup()
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        DataSiswaLayout.setVerticalGroup(
            DataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataSiswaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DataSiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        MainPanel.add(DataSiswa, "card4");

        DataPetugas.setBackground(new java.awt.Color(47, 0, 154));

        jPanel5.setBackground(new java.awt.Color(0, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Petugas 60px.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Data Petugas");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 102, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jTable_DataPetugas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable_DataPetugas);

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/refresh-button_1.png"))); // NOI18N
        jButton19.setText("Update");
        jButton19.setIconTextGap(10);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/delete.png"))); // NOI18N
        jButton20.setText("Delete");
        jButton20.setIconTextGap(10);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/turn-on.png"))); // NOI18N
        jButton21.setText("Reset");
        jButton21.setIconTextGap(10);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/add-dock.png"))); // NOI18N
        jButton22.setText("Insert");
        jButton22.setIconTextGap(10);
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jTextField_SearchPetugas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTextField_SearchPetugas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_SearchPetugas.setBorder(null);
        jTextField_SearchPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_SearchPetugasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_SearchPetugasKeyTyped(evt);
            }
        });

        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jTextField_SearchPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel83)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jButton22)
                .addGap(18, 18, 18)
                .addComponent(jButton19)
                .addGap(18, 18, 18)
                .addComponent(jButton20)
                .addGap(18, 18, 18)
                .addComponent(jButton21)
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTextField_SearchPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel83))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel20.setBackground(new java.awt.Color(102, 102, 102));
        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        jPanel20.setPreferredSize(new java.awt.Dimension(178, 661));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("ID                    :");

        jLabel77.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Kode Petugas :");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Nama               :");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Jenis Kelamin :");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Tempat Lahir :");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Tanggal Lahir :");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Alamat            :");

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("No Hp        :");

        jTextField_IdPetugas.setEditable(false);
        jTextField_IdPetugas.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_IdPetugas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_IdPetugas.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_IdPetugas.setBorder(null);
        jTextField_IdPetugas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_IdPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_IdPetugasActionPerformed(evt);
            }
        });

        jTextField_KodePetugas.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_KodePetugas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_KodePetugas.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_KodePetugas.setBorder(null);
        jTextField_KodePetugas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_KodePetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_KodePetugasActionPerformed(evt);
            }
        });

        jTextField_NamaPetugas.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_NamaPetugas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_NamaPetugas.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_NamaPetugas.setBorder(null);
        jTextField_NamaPetugas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_NamaPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_NamaPetugasActionPerformed(evt);
            }
        });

        jTextField_JenisKelaminPetugas.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_JenisKelaminPetugas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_JenisKelaminPetugas.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_JenisKelaminPetugas.setBorder(null);
        jTextField_JenisKelaminPetugas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_JenisKelaminPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_JenisKelaminPetugasActionPerformed(evt);
            }
        });

        jTextField_TempatLahirPetugas.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_TempatLahirPetugas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_TempatLahirPetugas.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_TempatLahirPetugas.setBorder(null);
        jTextField_TempatLahirPetugas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_TempatLahirPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_TempatLahirPetugasActionPerformed(evt);
            }
        });

        jTextField_TanggalLahirPetugas.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_TanggalLahirPetugas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_TanggalLahirPetugas.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_TanggalLahirPetugas.setBorder(null);
        jTextField_TanggalLahirPetugas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_TanggalLahirPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_TanggalLahirPetugasActionPerformed(evt);
            }
        });

        jTextField_AlamatPetugas.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_AlamatPetugas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_AlamatPetugas.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_AlamatPetugas.setBorder(null);
        jTextField_AlamatPetugas.setCaretColor(new java.awt.Color(255, 255, 255));

        jTextField_NoHpPetugas.setBackground(new java.awt.Color(102, 102, 102));
        jTextField_NoHpPetugas.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jTextField_NoHpPetugas.setForeground(new java.awt.Color(255, 255, 255));
        jTextField_NoHpPetugas.setBorder(null);
        jTextField_NoHpPetugas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField_NoHpPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_NoHpPetugasActionPerformed(evt);
            }
        });

        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/petugas 14 inch.png"))); // NOI18N
        jLabel79.setText("jLabel79");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel77)
                    .addComponent(jLabel29)
                    .addComponent(jLabel18)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField_AlamatPetugas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_TanggalLahirPetugas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_TempatLahirPetugas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_JenisKelaminPetugas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_KodePetugas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_IdPetugas, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NamaPetugas)
                    .addComponent(jTextField_NoHpPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField_IdPetugas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_KodePetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NamaPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jTextField_JenisKelaminPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_TempatLahirPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_TanggalLahirPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_AlamatPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NoHpPetugas, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addContainerGap())
        );

        javax.swing.GroupLayout DataPetugasLayout = new javax.swing.GroupLayout(DataPetugas);
        DataPetugas.setLayout(DataPetugasLayout);
        DataPetugasLayout.setHorizontalGroup(
            DataPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataPetugasLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(DataPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(DataPetugasLayout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        DataPetugasLayout.setVerticalGroup(
            DataPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataPetugasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DataPetugasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        MainPanel.add(DataPetugas, "card5");

        DataPeminjam.setBackground(new java.awt.Color(47, 0, 154));

        jPanel11.setBackground(new java.awt.Color(0, 51, 204));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Peminjam 60px.png"))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Data Peminjaman");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 153, 0));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel42.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel42.setText("Input Data Petugas");

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Kode Petugas");

        jLabel44.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Nama Petugas");

        jTextField_NamaPetugasPinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_NamaPetugasPinjamActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("No Hp Petugas");

        jComboBox_KodePetugasPeminjam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_KodePetugasPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_KodePetugasPeminjamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel45)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_NamaPetugasPinjam)
                            .addComponent(jTextField_NohpPetugasPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_KodePetugasPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel42))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jComboBox_KodePetugasPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NamaPetugasPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NohpPetugasPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 153, 0));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel46.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel46.setText("Input Data Siswa");

        jLabel47.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("NISN");

        jLabel48.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Nama Siswa");

        jLabel49.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Kelas");

        jLabel50.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("No Hp Siswa");

        jComboBox_NISNSiswaPeminjam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_NISNSiswaPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_NISNSiswaPeminjamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_NoHpSiswaPeminjam))
                    .addComponent(jLabel46)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel49)
                            .addComponent(jLabel48))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_NamaSiswaPeminjam)
                            .addComponent(jTextField_KelasSiswaPeminjam)
                            .addComponent(jComboBox_NISNSiswaPeminjam, 0, 125, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jComboBox_NISNSiswaPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NamaSiswaPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_KelasSiswaPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NoHpSiswaPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 153, 0));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel51.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel51.setText("Input Data Buku");

        jLabel52.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Kode Buku");

        jLabel53.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Judul Buku");

        jTextField_JudubBukuPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_JudubBukuPeminjamActionPerformed(evt);
            }
        });

        jLabel54.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Pengarang");

        jLabel55.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Jumlah Pinjam");

        jButton_TambahPinjam.setText("+");
        jButton_TambahPinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_TambahPinjamActionPerformed(evt);
            }
        });

        jButton5.setText("-");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField_KodeBukuPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_KodeBukuPeminjamActionPerformed(evt);
            }
        });
        jTextField_KodeBukuPeminjam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_KodeBukuPeminjamKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_JudubBukuPeminjam, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(jTextField_PengarangBukuPeminjam)))
                    .addComponent(jLabel51)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addGap(8, 8, 8)
                        .addComponent(jTextField_KodeBukuPeminjam))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jButton_TambahPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5)
                                .addGap(0, 22, Short.MAX_VALUE))
                            .addComponent(jTextField_JumlahPinjamBukuPeminjam))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jTextField_KodeBukuPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_JudubBukuPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_PengarangBukuPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel55)
                    .addComponent(jTextField_JumlahPinjamBukuPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_TambahPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jPanel15.setBackground(new java.awt.Color(255, 153, 0));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel57.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setText("Kode Pinjam");

        jLabel58.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Tanggal Pinjam");

        jDateChooser_TanggalPinjamPeminjam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser_TanggalPinjamPeminjamPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFiel_KodePinjamPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser_TanggalPinjamPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jTextFiel_KodePinjamPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58)
                    .addComponent(jDateChooser_TanggalPinjamPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(199, 178, 153));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jTable_PinjamAtas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "KODE_BUKU", "JUDUL_BUKU", "PENGARANG", "TOTAL_PINJAM"
            }
        ));
        jScrollPane6.setViewportView(jTable_PinjamAtas);

        jTable_Pinjam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jTable_Pinjam);

        jLabel56.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel56.setText("Total Pinjam");

        jTextField_TotalPinjamPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_TotalPinjamPeminjamActionPerformed(evt);
            }
        });

        jButton_InsertPinjam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/add-dock.png"))); // NOI18N
        jButton_InsertPinjam.setText("Insert");
        jButton_InsertPinjam.setIconTextGap(10);
        jButton_InsertPinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_InsertPinjamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(227, 227, 227)
                .addComponent(jButton_InsertPinjam)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField_TotalPinjamPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_TotalPinjamPeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_InsertPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DataPeminjamLayout = new javax.swing.GroupLayout(DataPeminjam);
        DataPeminjam.setLayout(DataPeminjamLayout);
        DataPeminjamLayout.setHorizontalGroup(
            DataPeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataPeminjamLayout.createSequentialGroup()
                .addGroup(DataPeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(DataPeminjamLayout.createSequentialGroup()
                        .addGroup(DataPeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DataPeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DataPeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        DataPeminjamLayout.setVerticalGroup(
            DataPeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataPeminjamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DataPeminjamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(DataPeminjamLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DataPeminjamLayout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(47, 47, Short.MAX_VALUE))
        );

        MainPanel.add(DataPeminjam, "card6");

        DataPengembalian.setBackground(new java.awt.Color(47, 0, 154));

        jPanel7.setBackground(new java.awt.Color(153, 153, 0));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/Data Pengembalian 60px.png"))); // NOI18N

        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 36)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Data Pengembalian");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31))
                .addGap(7, 7, 7))
        );

        jPanel8.setBackground(new java.awt.Color(48, 215, 205));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jTable_Pengembalian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "KODE_PENGEMBALIAN", "TANGGAL_KEMBALI", "KODE_PINJAM", "TANGGAL_PINJAM", "NAMA_SISWA", "JUMLAH_PINJAM", "KETERLAMBATAN", "DENDA"
            }
        ));
        jScrollPane4.setViewportView(jTable_Pengembalian);

        jPanel9.setBackground(new java.awt.Color(255, 153, 0));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel32.setText("Kode Pengembalian");

        jTextField_KodeKembaliPengembalian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_KodeKembaliPengembalianKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(jTextField_KodeKembaliPengembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jTextField_KodeKembaliPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 153, 0));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel33.setText("Kode Peminjaman");

        jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel34.setText("Tanggal Peminjaman");

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel35.setText("Nama Siswa");

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel36.setText("Jumlah Pinjam");

        jComboBox_KodePinjamPengembalian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_KodePinjamPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_KodePinjamPengembalianActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel35))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_NamaSiswaPengembalian)
                            .addComponent(jTextField_JumlahPinjamPengembalian)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_TanggalPinjamPengembalian)
                            .addComponent(jComboBox_KodePinjamPengembalian, 0, 149, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jComboBox_KodePinjamPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_TanggalPinjamPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_NamaSiswaPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_JumlahPinjamPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel37.setText("Tanggal Pengembalian");

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel38.setText("Keterlambatan");

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel39.setText("Denda");

        jTextField_DendaPengembalian.setBackground(new java.awt.Color(48, 215, 205));
        jTextField_DendaPengembalian.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jTextField_DendaPengembalian.setBorder(null);

        jButton_InsertPengembalian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gov/siput/library/icon/add-dock.png"))); // NOI18N
        jButton_InsertPengembalian.setText("Insert");
        jButton_InsertPengembalian.setIconTextGap(10);
        jButton_InsertPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_InsertPengembalianActionPerformed(evt);
            }
        });

        jTextField_TanggalKembaliPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_TanggalKembaliPengembalianActionPerformed(evt);
            }
        });

        jLabel84.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel84.setText("/ Hari");

        jLabel85.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel85.setText("Keterangan :");

        jLabel87.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel87.setText("Keterlambatan");

        jLabel88.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel88.setText("500 / Hari");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel38))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_TanggalKembaliPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jTextField_KeterlambatanPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel84))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39)
                                    .addComponent(jTextField_DendaPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel87)
                                    .addComponent(jLabel85)
                                    .addComponent(jLabel88))
                                .addGap(23, 23, 23)))
                        .addGap(42, 42, 42))
                    .addComponent(jScrollPane4))
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(334, 334, 334)
                .addComponent(jButton_InsertPengembalian)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jTextField_TanggalKembaliPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jTextField_KeterlambatanPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel84))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(jLabel85))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField_DendaPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel88))
                            .addComponent(jLabel87)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_InsertPengembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout DataPengembalianLayout = new javax.swing.GroupLayout(DataPengembalian);
        DataPengembalian.setLayout(DataPengembalianLayout);
        DataPengembalianLayout.setHorizontalGroup(
            DataPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataPengembalianLayout.createSequentialGroup()
                .addGroup(DataPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        DataPengembalianLayout.setVerticalGroup(
            DataPengembalianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataPengembalianLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        MainPanel.add(DataPengembalian, "card7");

        javax.swing.GroupLayout BackgroundPanelLayout = new javax.swing.GroupLayout(BackgroundPanel);
        BackgroundPanel.setLayout(BackgroundPanelLayout);
        BackgroundPanelLayout.setHorizontalGroup(
            BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundPanelLayout.createSequentialGroup()
                .addComponent(MenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        BackgroundPanelLayout.setVerticalGroup(
            BackgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BackgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_JudulBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_JudulBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_JudulBukuActionPerformed

    private void jTextField_PengarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_PengarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_PengarangActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        controllerbuku.updateDataBuku(this);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField_JenisKelaminPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_JenisKelaminPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_JenisKelaminPetugasActionPerformed

    private void jTextField_TempatLahirPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_TempatLahirPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_TempatLahirPetugasActionPerformed

    private void jTextField_NamaPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_NamaPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_NamaPetugasActionPerformed

    private void btnDatabukuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatabukuMouseEntered
        btnDatabuku.setBackground(new Color(0, 226, 214));
    }//GEN-LAST:event_btnDatabukuMouseEntered

    private void btnDatasiswaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatasiswaMouseEntered
        btnDatasiswa.setBackground(new Color(0, 226, 214));
    }//GEN-LAST:event_btnDatasiswaMouseEntered

    private void btnDatapetugasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapetugasMouseEntered
        btnDatapetugas.setBackground(new Color(0, 226, 214));
    }//GEN-LAST:event_btnDatapetugasMouseEntered

    private void btnDatapeminjamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapeminjamMouseEntered
        btnDatapeminjam.setBackground(new Color(0, 226, 214));
    }//GEN-LAST:event_btnDatapeminjamMouseEntered

    private void btnDatapengembalianMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapengembalianMouseEntered
        btnDatapengembalian.setBackground(new Color(0, 226, 214));
    }//GEN-LAST:event_btnDatapengembalianMouseEntered

    private void btnDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseEntered
        btnDashboard.setBackground(new Color(0, 226, 214));
    }//GEN-LAST:event_btnDashboardMouseEntered

    private void btnDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseExited
        btnDashboard.setBackground(new Color(60,0,184));
    }//GEN-LAST:event_btnDashboardMouseExited

    private void btnDatabukuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatabukuMouseExited
        btnDatabuku.setBackground(new Color(60,0,184));
    }//GEN-LAST:event_btnDatabukuMouseExited

    private void btnDatasiswaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatasiswaMouseExited
        btnDatasiswa.setBackground(new Color(60,0,184));
    }//GEN-LAST:event_btnDatasiswaMouseExited

    private void btnDatapetugasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapetugasMouseExited
        btnDatapetugas.setBackground(new Color(60,0,184));
    }//GEN-LAST:event_btnDatapetugasMouseExited

    private void btnDatapeminjamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapeminjamMouseExited
        btnDatapeminjam.setBackground(new Color(60,0,184));
    }//GEN-LAST:event_btnDatapeminjamMouseExited

    private void btnDatapengembalianMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapengembalianMouseExited
        btnDatapengembalian.setBackground(new Color(60,0,184));
    }//GEN-LAST:event_btnDatapengembalianMouseExited

    private void jLabel67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel67MouseClicked

    private void jLabel65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel65MouseClicked

    private void btnDatabukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatabukuMouseClicked

        //Remove Panel
        
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //Add Panel
        
        MainPanel.add(DataBuku);
        MainPanel.repaint();
        MainPanel.revalidate();
    }//GEN-LAST:event_btnDatabukuMouseClicked

    private void btnDatasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatasiswaMouseClicked
        //Remove Panel
        
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //Add Panel
        
        MainPanel.add(DataSiswa);
        MainPanel.repaint();
        MainPanel.revalidate();
    }//GEN-LAST:event_btnDatasiswaMouseClicked

    private void btnDatapetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapetugasMouseClicked
        //Remove Panel
        
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //Add Panel
        
        MainPanel.add(DataPetugas);
        MainPanel.repaint();
        MainPanel.revalidate();
    }//GEN-LAST:event_btnDatapetugasMouseClicked

    private void btnDatapeminjamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapeminjamMouseClicked
        
        jComboBox_KodePetugasPeminjam.removeAllItems();
        jComboBox_KodePetugasPeminjam.addItem("Select");
        try {
            String Sql ="SELECT*FROM petugas";
            Statement st= Con.createStatement();
            RsPetugas= st.executeQuery(Sql);
            while(RsPetugas.next()){
            String AliasKode= RsPetugas.getString("KODEPETUGAS");
            jComboBox_KodePetugasPeminjam.addItem(AliasKode);
        }
        } catch (Exception e) {
    JOptionPane.showMessageDialog(null,
            "Gagal Menampilkan Nama Petugas\n"+e.getMessage());
}
        
        jTextField_KodeBukuPeminjam.getText();
//        jComboBox_KodeBukuPeminjam.addItem("Select");
        try {
            String Sql ="SELECT*FROM BUKU";
            Statement st= Con.createStatement();
            RsBuku= st.executeQuery(Sql);
            while(RsBuku.next()){
            String AliasKode= RsBuku.getString("KODEBUKU");
            jTextField_KodeBukuPeminjam.getText();
        }
        } catch (Exception e) {
    JOptionPane.showMessageDialog(null,
            "Gagal Menampilkan Id Pelanggan\n"+e.getMessage());
}
        
        jComboBox_NISNSiswaPeminjam.removeAllItems();
        jComboBox_NISNSiswaPeminjam.addItem("Select");
        try {
            String Sql ="SELECT*FROM siswa";
            Statement st= Con.createStatement();
            RsAnggota= st.executeQuery(Sql);
            while(RsAnggota.next()){
            String AliasKode= RsAnggota.getString("NISN");
            jComboBox_NISNSiswaPeminjam.addItem(AliasKode);
        }
        } catch (Exception e) {
    JOptionPane.showMessageDialog(null,
            "Gagal Menampilkan Nama Siswa\n"+e.getMessage());
}


        //Remove Panel
        
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //Add Panel
        
        MainPanel.add(DataPeminjam);
        MainPanel.repaint();
        MainPanel.revalidate();
    }//GEN-LAST:event_btnDatapeminjamMouseClicked

    private void btnDatapengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapengembalianMouseClicked
        


        jComboBox_KodePinjamPengembalian.removeAllItems();
        jComboBox_KodePinjamPengembalian.addItem("Select");
        try {
            String Sql ="SELECT*FROM pinjam";
            Statement st= Con.createStatement();
            RsPinjam= st.executeQuery(Sql);
            while(RsPinjam.next()){
            String AliasKode= RsPinjam.getString("KODEPINJAM");
            jComboBox_KodePinjamPengembalian.addItem(AliasKode);
        }
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null,
            "Gagal Menampilkan Data\n"+e.getMessage());
}
        
        jComboBox_KodePinjamPengembalian.removeAllItems();
        jComboBox_KodePinjamPengembalian.addItem("Select");
        try {
            String Sql ="SELECT*FROM pinjam";
            Statement st= Con.createStatement();
            RsPinjam= st.executeQuery(Sql);
            while(RsPinjam.next()){
            String AliasKode= RsPinjam.getString("KODEPINJAM");
            jComboBox_KodePinjamPengembalian.addItem(AliasKode);
        }
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null,
            "Gagal Menampilkan Data\n"+e.getMessage());
        }
        
        
        
//        DefaultTableModel grid=new DefaultTableModel();
//        grid.addColumn("No");
//        grid.addColumn("Kode_pinjam");
//        grid.addColumn("Tanggal_Pinjam");
//        grid.addColumn("Kode_Petugas");
//        grid.addColumn("Nama_petugas");
//        grid.addColumn("Kode_Buku");
//        grid.addColumn("Judul_Buku");
//        grid.addColumn("NISN");
//        grid.addColumn("Nama_Siswa");
//        grid.addColumn("Kelas");
//        grid.addColumn("Jumlah_Pinjam");
//        try {
//           int i=1;
//           st=Con.createStatement();
//           Rs=st.executeQuery("SELECT*FROM pinjam");
//           while (Rs.next()){
//               grid.addRow(new Object[]{
//                   (""+i++),Rs.getString(1),Rs.getString(2),Rs.getString(3),
//                    Rs.getString(4),Rs.getString(5),Rs.getString(6),Rs.getString(7),
//                    Rs.getString(8),Rs.getString(9),Rs.getString(10),Rs.getString(10)
//               });
//               jTable_DetailPengembalian.setModel(grid);
//               jTable_DetailPengembalian.enable(true);
//               jButton_InsertPengembalian.requestFocus();
//           }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Gagal Tampil"+e);
//        }
        //Remove Panel
        
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //Add Panel
        
        MainPanel.add(DataPengembalian);
        MainPanel.repaint();
        MainPanel.revalidate();
        
        
    }//GEN-LAST:event_btnDatapengembalianMouseClicked

    private void btnDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseClicked
        ChartDataBuku();
        ChartDataSiswa();
        ChartDataPeminjam();
        
        //Remove Panel
        
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        
        //Add Panel
        
        MainPanel.add(MenuHome);
        MainPanel.repaint();
        MainPanel.revalidate();
    }//GEN-LAST:event_btnDashboardMouseClicked

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        controllerbuku.deleteDataBuku(this);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        controllerbuku.resetDataBuku(this);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        controllerbuku.insertDataBuku(this);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        controllersiswa.updateDataSiswa(this);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        controllersiswa.deleteDataSIswa(this);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        controllersiswa.resetDataSiswa(this);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        controllersiswa.insertDataSiswa(this);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        controllerpetugas.updateDataPetugas(this);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        controllerpetugas.deleteDataPetugas(this);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        controllerpetugas.resetDataPetugas(this);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        controllerpetugas.insertDataPetugas(this);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton_InsertPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_InsertPengembalianActionPerformed
        kode_kembali=jTextField_KodeKembaliPengembalian.getText();
        Tanggalkembali=jTextField_TanggalKembaliPengembalian.getText();
        kodepinjam=jComboBox_KodePinjamPengembalian.getItemAt(jComboBox_KodePinjamPengembalian.getSelectedIndex());
        Tanggalpinjam=jTextField_TanggalPinjamPengembalian.getText();
        namaanggota=jTextField_NamaSiswaPengembalian.getText();
        jumlahpinjam=Integer.parseInt(jTextField_JumlahPinjamPengembalian.getText());
        Keterlambatan=Integer.parseInt(jTextField_KeterlambatanPengembalian.getText());
        Denda=Integer.parseInt(jTextField_DendaPengembalian.getText());
        try {
            Sql="INSERT INTO pengembalian"
                    + "(KODEPENGEMBALIAN,TANGGALKEMBALI,KODEPINJAM,TANGGALPINJAM,NAMA,JUMLAHPINJAM,KETERLAMBATAN,DENDA)"
                    + "VALUES('"+ kode_kembali+"',"
                    + "'"+Tanggalkembali+"',"
                    + "'"+kodepinjam+"',"
                    + "'"+Tanggalpinjam+"',"
                    + "'"+namaanggota+"',"
                    + "'"+jumlahpinjam+"',"
                    + "'"+Keterlambatan+"',"
                    + "'"+Denda+"')";
            st=Con.createStatement();
            st.execute(Sql);
            kosongkanPengembalian();
            tampilPengembalian("SELECT*FROM pengembalian");
            JOptionPane.showMessageDialog(null, "Saving Succses");
            jButton_InsertPengembalian.enable(true);
                    
        } catch (Exception e) {
           JOptionPane.showConfirmDialog(null,"Data Tidak Tersimpan\n"+e.getMessage());
        }                    
    }//GEN-LAST:event_jButton_InsertPengembalianActionPerformed

    private void jTextField_IdPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_IdPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_IdPetugasActionPerformed

    private void jTextField_KodePetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_KodePetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_KodePetugasActionPerformed

    private void jTextField_NoHpPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_NoHpPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_NoHpPetugasActionPerformed

    private void jTextField_TanggalLahirPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_TanggalLahirPetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_TanggalLahirPetugasActionPerformed

    private void jComboBox_KodePetugasPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_KodePetugasPeminjamActionPerformed
        try {
            Sql ="select * from petugas where KODEPETUGAS='"+jComboBox_KodePetugasPeminjam.getSelectedItem().toString()+"'";
            st=Con.createStatement();
            RsPetugas=st.executeQuery(Sql);
            while(RsPetugas.next()){
                jTextField_NamaPetugasPinjam.setText(RsPetugas.getString("NAMA"));//sesuaikan di database, atau bisa di ubah menjadi("nama_pelanggan")
                jTextField_NohpPetugasPinjam.setText(RsPetugas.getString("NOHP"));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboBox_KodePetugasPeminjamActionPerformed

    private void jComboBox_NISNSiswaPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_NISNSiswaPeminjamActionPerformed
        try {
            Sql="select * from siswa where NISN='"+jComboBox_NISNSiswaPeminjam.getSelectedItem().toString()+"'";
            st=Con.createStatement();
            RsAnggota=st.executeQuery(Sql);
            while(RsAnggota.next()){
                jTextField_NamaSiswaPeminjam.setText(RsAnggota.getString("NAMA"));//sesuaikan di database, atau bisa di ubah menjadi("nama_pelanggan")
                jTextField_KelasSiswaPeminjam.setText(RsAnggota.getString("KELAS"));
                jTextField_NoHpSiswaPeminjam.setText(RsAnggota.getString("NOHP"));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboBox_NISNSiswaPeminjamActionPerformed

    private void jButton_InsertPinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_InsertPinjamActionPerformed
        kodepinjam=jTextFiel_KodePinjamPeminjam.getText();
        kodepetugas=jComboBox_KodePetugasPeminjam.getItemAt(jComboBox_KodePetugasPeminjam.getSelectedIndex()).toString();
        namapetugas=jTextField_NamaPetugasPinjam.getText();
        NISN=jComboBox_NISNSiswaPeminjam.getItemAt(jComboBox_NISNSiswaPeminjam.getSelectedIndex()).toString();
        namasiswa=jTextField_NamaSiswaPeminjam.getText();
        kelassiswa=jTextField_KelasSiswaPeminjam.getText();
        kodebuku=jTextField_KodeBukuPeminjam.getText();
        judulbuku=jTextField_JudubBukuPeminjam.getText();
        jumlahpinjam=Integer.parseInt(jTextField_TotalPinjamPeminjam.getText());
        simpandetail();
        try {
            Sql="insert into pinjam"
                    +"(KODEPINJAM,TANGGALPINJAM,KODEPETUGAS,NAMAPETUGAS,KODEBUKU,JUDULBUKU,NISN,NAMASISWA,KELAS,JUMLAHPINJAM)"
                    +"values('"+kodepinjam+"',"
                    + "'"+Tanggal+"',"
                    + "'"+kodepetugas+"',"
                    + "'"+namapetugas+"',"
                    + "'"+kodebuku+"',"
                    + "'"+judulbuku+"',"
                    + "'"+NISN+"',"
                    + "'"+namasiswa+"',"
                    + "'"+kelassiswa+"',"
                    + "'"+jumlahpinjam+"')";
            st=Con.createStatement();
            st.execute(Sql);
            kosongkanPeminjam();
            
            tampilpeminjaman();
            JOptionPane.showMessageDialog(null, "Data successfully saved");
            hapustable();
            jButton_TambahPinjam.show();
            jButton_InsertPinjam.show();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data is not successfully saved, Data that you entered is incorrect"+e.getMessage());
        }
    }//GEN-LAST:event_jButton_InsertPinjamActionPerformed

    private void jTextField_NamaPetugasPinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_NamaPetugasPinjamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_NamaPetugasPinjamActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        DefaultTableModel model = (DefaultTableModel)jTable_PinjamAtas.getModel();
        int row = jTable_PinjamAtas.getSelectedRow();
            if (row>=0) {
                int ok = JOptionPane.showConfirmDialog(null, "You sure you want to Delete","Message",JOptionPane.YES_NO_OPTION);
            
                if (ok==0){
                    model.removeRow(row);
                }
                }
            Total();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton_TambahPinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_TambahPinjamActionPerformed
        prosestambah();
        Total();
    }//GEN-LAST:event_jButton_TambahPinjamActionPerformed

    private void jTextField_TotalPinjamPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_TotalPinjamPeminjamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_TotalPinjamPeminjamActionPerformed

    private void jDateChooser_TanggalPinjamPeminjamPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser_TanggalPinjamPeminjamPropertyChange
            if(jDateChooser_TanggalPinjamPeminjam.getDate()!=null){
            SimpleDateFormat format =new SimpleDateFormat ("yyyy-MM-dd");
            Tanggal=format.format(jDateChooser_TanggalPinjamPeminjam.getDate());
            }
    }//GEN-LAST:event_jDateChooser_TanggalPinjamPeminjamPropertyChange

    private void jTextField_JudubBukuPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_JudubBukuPeminjamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_JudubBukuPeminjamActionPerformed

    private void btnDashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDashboardMousePressed

    private void jTextField_KodeKembaliPengembalianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_KodeKembaliPengembalianKeyPressed
        kode_kembali=jTextField_KodeKembaliPengembalian.getText();
        int A = evt.getKeyCode();
        if (A==10){
            try {
                Sql="SELECT * FROM pengembalian WHERE KODEPENGEMBALIAN='"+kode_kembali+"'";
                st=Con.createStatement();
                Rs=st.executeQuery(Sql);
                while(Rs.next()){
                jComboBox_KodePinjamPengembalian.setSelectedItem(Rs.getString(3));
                jTextField_TanggalKembaliPengembalian.setText(Rs.getString(2));
                jTextField_NamaSiswaPengembalian.setText(Rs.getString(4));
                jTextField_JumlahPinjamPengembalian.setText(Rs.getString(5));
                jTextField_TanggalKembaliPengembalian.setText(Rs.getString(6));
                jTextField_KeterlambatanPengembalian.setText(Rs.getString(7));
                jTextField_DendaPengembalian.setText(Rs.getString(8));
                jButton_InsertPengembalian.enable(false);
                jComboBox_KodePinjamPengembalian.enable(false);
                jTextField_NamaSiswaPengembalian.enable(false);
                jTextField_JumlahPinjamPengembalian.enable(false);
                jTextField_TanggalKembaliPengembalian.enable(false);
                }
            } catch (Exception e) {
                    JOptionPane.showConfirmDialog(null, "Data Not Found\n"+e.getMessage());
                    jTextField_KodeKembaliPengembalian.requestFocus();
            }
        }
    }//GEN-LAST:event_jTextField_KodeKembaliPengembalianKeyPressed

    private void jTextField_TanggalKembaliPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_TanggalKembaliPengembalianActionPerformed
          DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            try{

            //Konversi dari string ke tanggal
            java.util.Date tanggalpinjam = df.parse(jTextField_TanggalPinjamPengembalian.getText());
            java.util.Date tanggalkembali = df.parse(jTextField_TanggalKembaliPengembalian.getText());

            //Tgl di konversi ke milidetik
            long Hari1 = tanggalpinjam.getTime();
            long Hari2 = tanggalkembali.getTime();
            long diff = Hari2 - Hari1;
            long Lama = diff / (24 * 60 * 60 * 1000);
            
            if(Lama <=6){
                Lama=0;
            }else if(Lama >=7 && Lama <=7){
                Lama=1;
            } else if(Lama >=8 && Lama <=8) {
                Lama=2;
            }else if(Lama >=9 && Lama <=9){
                Lama=3;
            }else if(Lama >=10 && Lama <=10){
                Lama=4;
            }else if(Lama >=11 && Lama <=11){
                Lama=5;
            }else if(Lama >=12 && Lama <=12){
                Lama=6;
            }
            
            

            jTextField_KeterlambatanPengembalian.setText(Long.toString(Lama));

            Keterlambatan=Integer.parseInt(jTextField_KeterlambatanPengembalian.getText());

            Hasil = 500*Keterlambatan;
            jTextField_DendaPengembalian.setText(String.valueOf(Hasil));
            } catch (ParseException e)
            {
            e.printStackTrace();
            }
    }//GEN-LAST:event_jTextField_TanggalKembaliPengembalianActionPerformed

    private void jComboBox_KodePinjamPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_KodePinjamPengembalianActionPerformed
         try {
            
            Sql="select * from pinjam where KODEPINJAM='"+jComboBox_KodePinjamPengembalian.getSelectedItem().toString()+"'";
            st=Con.createStatement();
            RsPinjam=st.executeQuery(Sql);
            while(RsPinjam.next()){
                jTextField_TanggalPinjamPengembalian.setText(RsPinjam.getString("TANGGALPINJAM"));
                jTextField_NamaSiswaPengembalian.setText(RsPinjam.getString("NAMASISWA"));//sesuaikan di database, atau bisa di ubah menjadi("nama_pelanggan")
                jTextField_JumlahPinjamPengembalian.setText(RsPinjam.getString("JUMLAHPINJAM"));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jComboBox_KodePinjamPengembalianActionPerformed

    private void jTextField_JumlahBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_JumlahBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_JumlahBukuActionPerformed

    private void jTextField_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_IDActionPerformed

    private void btnDatapengembalianMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDatapengembalianMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDatapengembalianMousePressed

    private void jTextField_KodeBukuPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_KodeBukuPeminjamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_KodeBukuPeminjamActionPerformed

    private void jTextField_KodeBukuPeminjamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_KodeBukuPeminjamKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
            FunctionKodeBuku f = new FunctionKodeBuku();
            ResultSet rs = null;
            String jdl = "JUDULBUKU";
            String png = "PENGARANG";

            rs = f.find(jTextField_KodeBukuPeminjam.getText());
            try{
              if(rs.next()){
                  jTextField_JudubBukuPeminjam.setText(rs.getString("JUDULBUKU"));
                    jTextField_PengarangBukuPeminjam.setText(rs.getString("PENGARANG"));
              }  else{
                  JOptionPane.showMessageDialog(null, "Kode Buku Tidak di Temukan !");
              }
            }catch(Exception ex){
                   JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
        }
    }//GEN-LAST:event_jTextField_KodeBukuPeminjamKeyPressed

    private void jTextField_SearchBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SearchBukuKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("ID");
        table.addColumn("KODEBUKU");
        table.addColumn("JUDULBUKU");
        table.addColumn("PENGARANG");
        table.addColumn("TAHUNTERBIT");
        table.addColumn("JUMLAHBUKU");
        table.addColumn("PENERBIT");
        
        try
        {
            
            String sql = "SELECT * FROM buku WHERE KODEBUKU='"+jTextField_SearchBuku.getText()+"'";
            Statement S = siputDatabase.getConnection().createStatement();
            ResultSet R = S.executeQuery(sql);
            
            while(R.next())
            {
                table.addRow(new Object[]{
                    R.getString(1),
                    R.getString(2),
                    R.getString(3),
                    R.getString(4),
                    R.getString(5),
                    R.getString(6),
                    R.getString(7),
                        
                });
            }
            jTable_DataBuku.setModel(table);
        }
        catch(Exception e){
    
}
        }
    }//GEN-LAST:event_jTextField_SearchBukuKeyPressed

    private void jTextField_SearchBukuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SearchBukuKeyTyped
       if("".equals(jTextField_SearchBuku.getText())){
            {
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("ID");
        table.addColumn("KODEBUKU");
        table.addColumn("JUDULBUKU");
        table.addColumn("PENGARANG");
        table.addColumn("TAHUNTERBIT");
        table.addColumn("JUMLAHBUKU");
        table.addColumn("PENERBIT");
        
        try
        {
            
            String sql = "SELECT * FROM buku";
            Statement S = siputDatabase.getConnection().createStatement();
            ResultSet R = S.executeQuery(sql);
            
            while(R.next())
            {
                table.addRow(new Object[]{
                    R.getString(1),
                    R.getString(2),
                    R.getString(3),
                    R.getString(4),
                    R.getString(5),
                    R.getString(6),
                    R.getString(7),
                        
                });
            }
            jTable_DataBuku.setModel(table);
        }
        catch(Exception e){
    
}
        }
        }
    }//GEN-LAST:event_jTextField_SearchBukuKeyTyped

    private void jTextField_SearchSiswaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SearchSiswaKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("ID");
        table.addColumn("NISN");
        table.addColumn("NAMA");
        table.addColumn("KELAS");
        table.addColumn("JENISKELAMIN");
        table.addColumn("TEMPATLAHIR");
        table.addColumn("TANGGALLAHIR");
        table.addColumn("ALAMAT");
        table.addColumn("NOHP");
        
        try
        {
            
            String sql = "SELECT * FROM siswa WHERE NAMA='"+jTextField_SearchSiswa.getText()+"'";
            Statement S = siputDatabase.getConnection().createStatement();
            ResultSet R = S.executeQuery(sql);
            
            while(R.next())
            {
                table.addRow(new Object[]{
                    R.getString(1),
                    R.getString(2),
                    R.getString(3),
                    R.getString(4),
                    R.getString(5),
                    R.getString(6),
                    R.getString(7),
                    R.getString(8),
                    R.getString(9),
                        
                });
            }
            jTable_DataSiswa.setModel(table);
        }
        catch(Exception e){
    
}
        }
    }//GEN-LAST:event_jTextField_SearchSiswaKeyPressed

    private void jTextField_SearchSiswaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SearchSiswaKeyTyped
        if("".equals(jTextField_SearchSiswa.getText())){
           {
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("ID");
        table.addColumn("NISN");
        table.addColumn("NAMA");
        table.addColumn("KELAS");
        table.addColumn("JENISKELAMIN");
        table.addColumn("TEMPATLAHIR");
        table.addColumn("TANGGALLAHIR");
        table.addColumn("ALAMAT");
        table.addColumn("NOHP");
        
        try
        {
            
            String sql = "SELECT * FROM siswa";
            Statement S = siputDatabase.getConnection().createStatement();
            ResultSet R = S.executeQuery(sql);
            
            while(R.next())
            {
                table.addRow(new Object[]{
                    R.getString(1),
                    R.getString(2),
                    R.getString(3),
                    R.getString(4),
                    R.getString(5),
                    R.getString(6),
                    R.getString(7),
                    R.getString(8),
                    R.getString(9),
                        
                });
            }
            jTable_DataSiswa.setModel(table);
        }
        catch(Exception e){
    
}
        } 
        }
    }//GEN-LAST:event_jTextField_SearchSiswaKeyTyped

    private void jTextField_SearchPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SearchPetugasKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("ID");
        table.addColumn("KODE");
        table.addColumn("NAMA");
        table.addColumn("JENISKELAMIN");
        table.addColumn("TEMPATLAHIR");
        table.addColumn("TANGGALLAHIR");
        table.addColumn("ALAMAT");
        table.addColumn("NOHP");
        
        try
        {
            
            String sql = "SELECT * FROM petugas WHERE NAMA='"+jTextField_SearchPetugas.getText()+"'";
            Statement S = siputDatabase.getConnection().createStatement();
            ResultSet R = S.executeQuery(sql);
            
            while(R.next())
            {
                table.addRow(new Object[]{
                    R.getString(1),
                    R.getString(2),
                    R.getString(3),
                    R.getString(4),
                    R.getString(5),
                    R.getString(6),
                    R.getString(7),
                    R.getString(8),
                        
                });
            }
            jTable_DataPetugas.setModel(table);
        }
        catch(Exception e){
    
}
        }
    }//GEN-LAST:event_jTextField_SearchPetugasKeyPressed

    private void jTextField_SearchPetugasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_SearchPetugasKeyTyped
        if("".equals(jTextField_SearchPetugas.getText())){
            {
        DefaultTableModel table = new DefaultTableModel();
        
        table.addColumn("ID");
        table.addColumn("KODE");
        table.addColumn("NAMA");
        table.addColumn("JENISKELAMIN");
        table.addColumn("TEMPATLAHIR");
        table.addColumn("TANGGALLAHIR");
        table.addColumn("ALAMAT");
        table.addColumn("NOHP");
        
        try
        {
            
            String sql = "SELECT * FROM petugas";
            Statement S = siputDatabase.getConnection().createStatement();
            ResultSet R = S.executeQuery(sql);
            
            while(R.next())
            {
                table.addRow(new Object[]{
                    R.getString(1),
                    R.getString(2),
                    R.getString(3),
                    R.getString(4),
                    R.getString(5),
                    R.getString(6),
                    R.getString(7),
                    R.getString(8),
                        
                });
            }
            jTable_DataPetugas.setModel(table);
        }
        catch(Exception e){
    
}
        }
            
        }
    }//GEN-LAST:event_jTextField_SearchPetugasKeyTyped

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        btnLogout.setBackground(new Color(0, 226, 214));
        RegisterView register = new RegisterView();
        register.setVisible(true);
        register.pack();
//        lgf.setLocationRelativeTo(null);
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        btnLogout.setBackground(new Color(0, 226, 214));
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        btnLogout.setBackground(new Color(60,0,184));
    }//GEN-LAST:event_btnLogoutMouseExited

    private void btnLogoutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMousePressed
        btnLogout.setBackground(new Color(0, 226, 214));
    }//GEN-LAST:event_btnLogoutMousePressed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jTextField_KodeRakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_KodeRakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_KodeRakActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashboardMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardMainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DashboardMainView().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(DashboardMainView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DataBukuException ex) {
                    Logger.getLogger(DashboardMainView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DataSiswaException ex) {
                    Logger.getLogger(DashboardMainView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DataPetugasException ex) {
                    Logger.getLogger(DashboardMainView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackgroundPanel;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JPanel DataBuku;
    private javax.swing.JPanel DataPeminjam;
    private javax.swing.JPanel DataPengembalian;
    private javax.swing.JPanel DataPetugas;
    private javax.swing.JPanel DataSiswa;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel MenuHome;
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JPanel btnDashboard;
    private javax.swing.JPanel btnDatabuku;
    private javax.swing.JPanel btnDatapeminjam;
    private javax.swing.JPanel btnDatapengembalian;
    private javax.swing.JPanel btnDatapetugas;
    private javax.swing.JPanel btnDatasiswa;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton_InsertPengembalian;
    private javax.swing.JButton jButton_InsertPinjam;
    private javax.swing.JButton jButton_TambahPinjam;
    private javax.swing.JComboBox<String> jComboBox_KodePetugasPeminjam;
    private javax.swing.JComboBox<String> jComboBox_KodePinjamPengembalian;
    private javax.swing.JComboBox<String> jComboBox_NISNSiswaPeminjam;
    private com.toedter.calendar.JDateChooser jDateChooser_TanggalPinjamPeminjam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    public javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTable jTable_DataBuku;
    private javax.swing.JTable jTable_DataPetugas;
    private javax.swing.JTable jTable_DataSiswa;
    private javax.swing.JTable jTable_Pengembalian;
    private javax.swing.JTable jTable_Pinjam;
    private javax.swing.JTable jTable_PinjamAtas;
    private javax.swing.JTextField jTextFiel_KodePinjamPeminjam;
    private javax.swing.JTextField jTextField_AlamatPetugas;
    private javax.swing.JTextField jTextField_AlamatSiswa;
    private javax.swing.JTextField jTextField_DendaPengembalian;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_IdPetugas;
    private javax.swing.JTextField jTextField_IdSiswa;
    private javax.swing.JTextField jTextField_JenisKelamin;
    private javax.swing.JTextField jTextField_JenisKelaminPetugas;
    private javax.swing.JTextField jTextField_JudubBukuPeminjam;
    private javax.swing.JTextField jTextField_JudulBuku;
    private javax.swing.JTextField jTextField_JumlahBuku;
    private javax.swing.JTextField jTextField_JumlahPinjamBukuPeminjam;
    private javax.swing.JTextField jTextField_JumlahPinjamPengembalian;
    private javax.swing.JTextField jTextField_Kelas;
    private javax.swing.JTextField jTextField_KelasSiswaPeminjam;
    private javax.swing.JTextField jTextField_KeterlambatanPengembalian;
    private javax.swing.JTextField jTextField_KodeBuku;
    private javax.swing.JTextField jTextField_KodeBukuPeminjam;
    private javax.swing.JTextField jTextField_KodeKembaliPengembalian;
    private javax.swing.JTextField jTextField_KodePetugas;
    private javax.swing.JTextField jTextField_KodeRak;
    private javax.swing.JTextField jTextField_NISN;
    private javax.swing.JTextField jTextField_NamaPetugas;
    private javax.swing.JTextField jTextField_NamaPetugasPinjam;
    private javax.swing.JTextField jTextField_NamaSiswa;
    private javax.swing.JTextField jTextField_NamaSiswaPeminjam;
    private javax.swing.JTextField jTextField_NamaSiswaPengembalian;
    private javax.swing.JTextField jTextField_NoHpPetugas;
    private javax.swing.JTextField jTextField_NoHpSiswa;
    private javax.swing.JTextField jTextField_NoHpSiswaPeminjam;
    private javax.swing.JTextField jTextField_NohpPetugasPinjam;
    private javax.swing.JTextField jTextField_Penerbit;
    private javax.swing.JTextField jTextField_Pengarang;
    private javax.swing.JTextField jTextField_PengarangBukuPeminjam;
    private javax.swing.JTextField jTextField_SearchBuku;
    private javax.swing.JTextField jTextField_SearchPetugas;
    private javax.swing.JTextField jTextField_SearchSiswa;
    private javax.swing.JTextField jTextField_TahunTerbit;
    private javax.swing.JTextField jTextField_TanggalKembaliPengembalian;
    private javax.swing.JTextField jTextField_TanggalLahirPetugas;
    private javax.swing.JTextField jTextField_TanggalLahirSiswa;
    private javax.swing.JTextField jTextField_TanggalPinjamPengembalian;
    private javax.swing.JTextField jTextField_TempatLahirPetugas;
    private javax.swing.JTextField jTextField_TempatLahirSiswa;
    private javax.swing.JTextField jTextField_TotalPinjamPeminjam;
    private javax.swing.JPanel panelChart;
    private javax.swing.JPanel panelchart1;
    private javax.swing.JPanel panelchart2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onChangeBuku(DataBukuModel databuku) {
        jTextField_ID.setText(modelbuku.getId()+"");
        jTextField_KodeBuku.setText(modelbuku.getKodebuku()+"");
        jTextField_JudulBuku.setText(modelbuku.getJudulbuku()+"");
        jTextField_Pengarang.setText(modelbuku.getPengarang()+"");
        jTextField_TahunTerbit.setText(modelbuku.getTahunterbit()+"");
        jTextField_Penerbit.setText(modelbuku.getPenerbit()+"");
        jTextField_JumlahBuku.setText(modelbuku.getJumlahbuku()+"");
        jTextField_KodeRak.setText(modelbuku.getKoderak()+"");

   }

    @Override
    public void OnInsertBuku(DataBuku databuku) {
        tablemodelbuku.add(databuku);
    }

    @Override
    public void onDeleteBuku() {
        int index = jTable_DataBuku.getSelectedRow();
        tablemodelbuku.remove(index);
    }

    @Override
    public void onUpdateBuku(DataBuku databuku) {
        int index = jTable_DataBuku.getSelectedRow();
        tablemodelbuku.set(index, databuku);
    }

    
    @Override
    public void valueChanged(ListSelectionEvent e) {
            try{
            DataBuku modelbuku = tablemodelbuku.get(jTable_DataBuku.getSelectedRow());
            jTextField_ID.setText(modelbuku.getId()+"");
            jTextField_KodeBuku.setText(modelbuku.getKodebuku()+"");
            jTextField_JudulBuku.setText(modelbuku.getJudulbuku()+"");
            jTextField_Pengarang.setText(modelbuku.getPengarang()+"");
            jTextField_TahunTerbit.setText(modelbuku.getTahunterbit()+"");
            jTextField_Penerbit.setText(modelbuku.getPenerbit()+"");
            jTextField_JumlahBuku.setText(modelbuku.getJumlahbuku()+"");
            jTextField_KodeRak.setText(modelbuku.getKoderak()+"");

            
            DataSiswa modelsiswa = tablemodelsiswa.get(jTable_DataSiswa.getSelectedRow());
            jTextField_IdSiswa.setText(modelsiswa.getIdsiswa()+"");
            jTextField_NISN.setText(modelsiswa.getNisn()+"");
            jTextField_NamaSiswa.setText(modelsiswa.getNamasiswa()+"");
            jTextField_Kelas.setText(modelsiswa.getKelas()+"");
            jTextField_JenisKelamin.setText(modelsiswa.getJeniskelaminsiswa()+"");
            jTextField_TempatLahirSiswa.setText(modelsiswa.getTempatlahirsiswa()+"");
            jTextField_TanggalLahirSiswa.setText(modelsiswa.getTanggallahirsiswa()+"");
            jTextField_AlamatSiswa.setText(modelsiswa.getAlamatsiswa()+"");
            jTextField_NoHpSiswa.setText(modelsiswa.getNohpsiswa()+"");
            
            DataPetugas modelpetugas = tablemodelpetugas.get(jTable_DataPetugas.getSelectedRow());
            jTextField_IdPetugas.setText(modelpetugas.getIdpetugas()+"");
            jTextField_KodePetugas.setText(modelpetugas.getKodepetugas()+"");
            jTextField_NamaPetugas.setText(modelpetugas.getNamapetugas()+"");
            jTextField_JenisKelaminPetugas.setText(modelpetugas.getJeniskelaminpetugas()+"");
            jTextField_TempatLahirPetugas.setText(modelpetugas.getTempatlahirpetugas()+"");
            jTextField_TanggalLahirPetugas.setText(modelpetugas.getTanggallahirpetugas()+"");
            jTextField_AlamatPetugas.setText(modelpetugas.getAlamatpetugas()+"");
            jTextField_NoHpPetugas.setText(modelpetugas.getNohppetugas()+"");
            
            
        }catch (IndexOutOfBoundsException exception){
            
        }
    }
    
        public void loadDatabase() throws SQLException, DataBukuException, DataSiswaException, DataPetugasException{
        DataBukuDao dao = siputDatabase.getDataBukuDao();
        tablemodelbuku.setList(dao.selectAllDataBuku());
        
        DataSiswaDao dao1 = siputDatabase.getDataSiswaDao();
        tablemodelsiswa.setList(dao1.selectAllDataSiswa());
        
        DataPetugasDao dao2 = siputDatabase.getDataPetugasDao();
        tablemodelpetugas.setList(dao2.selectAllDataPetugas());
       
        
    }

    @Override
    public void onChangeSiswa(DataSiswaModel datasiswa) {
        jTextField_IdSiswa.setText(modelsiswa.getIdsiswa()+"");
        jTextField_NISN.setText(modelsiswa.getNisn()+"");
        jTextField_NamaSiswa.setText(modelsiswa.getNamasiswa()+"");
        jTextField_Kelas.setText(modelsiswa.getKelas()+"");
        jTextField_JenisKelamin.setText(modelsiswa.getJeniskelaminsiswa()+"");
        jTextField_TempatLahirSiswa.setText(modelsiswa.getTempatlahirsiswa()+"");
        jTextField_TanggalLahirSiswa.setText(modelsiswa.getTanggallahirsiswa()+"");
        jTextField_AlamatSiswa.setText(modelsiswa.getAlamatsiswa()+"");
        jTextField_NoHpSiswa.setText(modelsiswa.getNohpsiswa()+"");
    }

    @Override
    public void OnInsertSiswa(DataSiswa datasiswa) {
        tablemodelsiswa.add(datasiswa);
    }

    @Override
    public void onDeleteSiswa() {
        int index = jTable_DataSiswa.getSelectedRow();
        tablemodelsiswa.remove(index);
    }

    @Override
    public void onUpdateSiswa(DataSiswa datasiswa) {
        int index = jTable_DataSiswa.getSelectedRow();
        tablemodelsiswa.set(index, datasiswa);
    }

    @Override
    public void onChangePetugas(DataPetugasModel datapetugas) {
        jTextField_IdPetugas.setText(modelpetugas.getIdpetugas()+"");
        jTextField_KodePetugas.setText(modelpetugas.getKodepetugas()+"");
        jTextField_NamaPetugas.setText(modelpetugas.getNamapetugas()+"");
        jTextField_JenisKelaminPetugas.setText(modelpetugas.getJeniskelaminpetugas()+"");
        jTextField_TempatLahirPetugas.setText(modelpetugas.getTempatlahirpetugas()+"");
        jTextField_TanggalLahirPetugas.setText(modelpetugas.getTanggallahirpetugas()+"");
        jTextField_AlamatPetugas.setText(modelpetugas.getAlamatpetugas()+"");
        jTextField_NoHpPetugas.setText(modelpetugas.getNohppetugas()+"");
    }

    @Override
    public void onInsertPetugas(DataPetugas datapetugas) {
        tablemodelpetugas.add(datapetugas);
    }

    @Override
    public void onDeletePetugas() {
        int index = jTable_DataPetugas.getSelectedRow();
        tablemodelpetugas.remove(index);
    }

    @Override
    public void onUpdatePetugas(DataPetugas datapetugas) {
        int index = jTable_DataPetugas.getSelectedRow();
        tablemodelpetugas.set(index, datapetugas);
    }
    
    
    
}
