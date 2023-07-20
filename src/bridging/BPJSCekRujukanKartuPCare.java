/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import khanzahmsanjungan.DlgCariDokter;
//import simrskhanza.DlgPasien;
//import simrskhanza.DlgPilihanCetakDokumen;

/**
 *
 * @author dosen
 */
public final class BPJSCekRujukanKartuPCare extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private final Properties prop = new Properties();
    private validasi Valid = new validasi();
    private sekuel Sequel = new sekuel();
    private int i = 0;
    private Connection koneksi = koneksiDB.condb();
    private BPJSCekReferensiFaskes faskes = new BPJSCekReferensiFaskes(null, false);
    private BPJSCekReferensiPenyakit penyakit = new BPJSCekReferensiPenyakit(null, false);
    private BPJSCekMappingPoli poli = new BPJSCekMappingPoli(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private BPJSCekReferensiDokterDPJP dpjp = new BPJSCekReferensiDokterDPJP(null, true);
    private BPJSSuratKontrol skdp = new BPJSSuratKontrol(null, false);
    private BPJSSPRI skdp2 = new BPJSSPRI(null, false);
    private BPJSCekReferensiPropinsi propinsikll = new BPJSCekReferensiPropinsi(null, false);
    private BPJSCekReferensiKabupaten kabupatenkll = new BPJSCekReferensiKabupaten(null, false);
    private BPJSCekReferensiKecamatan kecamatankll = new BPJSCekReferensiKecamatan(null, false);
    private ApiBPJS api = new ApiBPJS();
    private int pilih = 0, p_no_ktp = 0, p_tmp_lahir = 0, p_nm_ibu = 0, p_alamat = 0,
            p_pekerjaan = 0, p_no_tlp = 0, p_umur = 0, p_namakeluarga = 0, p_no_peserta = 0,
            p_kelurahan = 0, p_kecamatan = 0, p_kabupaten = 0, p_pekerjaanpj = 0,
            p_alamatpj = 0, p_kelurahanpj = 0, p_kecamatanpj = 0, p_kabupatenpj = 0, jmlhari = 0,
            p_propinsi = 0, p_propinsipj = 0;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private String kdkel = "", kdkec = "", kdkab = "", kdprop = "", nosisrute = "", BASENOREG = "", URUTNOREG = "", link = "", klg = "SAUDARA", statuspasien = "", pengurutan = "", tahun = "", bulan = "", posisitahun = "", awalantahun = "", awalanbulan = "",
            no_ktp = "", tmp_lahir = "", nm_ibu = "", alamat = "", pekerjaan = "", no_tlp = "", tglkkl = "0000-00-00",
            umur = "", umurdaftar = "0", namakeluarga = "", no_peserta = "", kelurahan = "", kecamatan = "", sttsumur = "",
            kabupaten = "", pekerjaanpj = "", alamatpj = "", kelurahanpj = "", kecamatanpj = "",
            kabupatenpj = "", hariawal = "", requestJson, URL = "", nosep = "", user = "", prb = "", peserta = "",
            status = "Baru", propinsi = "", propinsipj = "", utc = "",
            tampilkantni = Sequel.cariIsi("select tampilkan_tni_polri from set_tni_polri");
    private PreparedStatement ps, pskelengkapan, pscariumur, pssetalamat, pstni, pspolri;
    private ResultSet rs, rs2;
    private double biaya = 0;
    private Date lahir;
    private LocalDate today = LocalDate.now();
    private LocalDate birthday;
    private Period p;
    private long p2;
//    private DlgPasien pasien = new DlgPasien(null, false);
    private Calendar cal = Calendar.getInstance();
    private int day = cal.get(Calendar.DAY_OF_WEEK);
    private boolean empt = false;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private String hari = "";

    /**
     * Creates new form DlgKamar
     *
     * @param parent
     * @param modal
     */
    public BPJSCekRujukanKartuPCare(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10, 2);
        setSize(628, 674);

        Object[] row = {"", ""};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        faskes.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (faskes.getTable().getSelectedRow() != -1) {
                    KdPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(), 1).toString());
                    NmPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(), 2).toString());
                }
                KdPpkRujukan.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        faskes.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    faskes.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (penyakit.getTable().getSelectedRow() != -1) {
                    KdPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(), 1).toString());
                    NmPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(), 2).toString());
                }
                KdPenyakit.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        penyakit.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    penyakit.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli.getTable().getSelectedRow() != -1) {
                    kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 0).toString());
                    TPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 2).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 3).toString());
                    isNumber();
                    KdPoli.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        poli.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    poli.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    isNumber();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

//        kamar.addWindowListener(new WindowListener() {
//            @Override
//            public void windowOpened(WindowEvent e) {
//            }
//
//            @Override
//            public void windowClosing(WindowEvent e) {
//            }
//
//            @Override
//            public void windowClosed(WindowEvent e) {
//                if (kamar.getTable().getSelectedRow() != -1) {
//                    if (kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(), 6).toString().equals("KOSONG")) {
//                        KdPoli.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(), 1).toString());
//                        NmPoli.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(), 3).toString());
//                        TBiaya.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(), 5).toString());
//                        KdPoli.requestFocus();
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Maaf, status kamar isi. Silahkan cari yang kosong..!!");
//                        KdPoli.requestFocus();
//                    }
//                }
//            }
//
//            @Override
//            public void windowIconified(WindowEvent e) {
//            }
//
//            @Override
//            public void windowDeiconified(WindowEvent e) {
//            }
//
//            @Override
//            public void windowActivated(WindowEvent e) {
//            }
//
//            @Override
//            public void windowDeactivated(WindowEvent e) {
//            }
//        });
//
//        kamar.getTable().addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                    kamar.dispose();
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//            }
//        });
        dpjp.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dpjp.getTable().getSelectedRow() != -1) {
                    if (pilih == 1) {
                        KdDPJP.setText(dpjp.getTable().getValueAt(dpjp.getTable().getSelectedRow(), 1).toString());
                        NmDPJP.setText(dpjp.getTable().getValueAt(dpjp.getTable().getSelectedRow(), 2).toString());
                        if (JenisPelayanan.getSelectedIndex() == 1) {
                            KdDPJPLayanan.setText(dpjp.getTable().getValueAt(dpjp.getTable().getSelectedRow(), 1).toString());
                            NmDPJPLayanan.setText(dpjp.getTable().getValueAt(dpjp.getTable().getSelectedRow(), 2).toString());
                        }
                        try {
                            ps = koneksi.prepareStatement(
                                    "select maping_dokter_dpjpvclaim.kd_dokter,dokter.nm_dokter from maping_dokter_dpjpvclaim inner join dokter "
                                    + "on maping_dokter_dpjpvclaim.kd_dokter=dokter.kd_dokter where maping_dokter_dpjpvclaim.kd_dokter_bpjs=?");
                            try {
                                ps.setString(1, KdDPJP.getText());
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    kddokter.setText(rs.getString("kd_dokter"));
                                    TDokter.setText(rs.getString("nm_dokter"));
                                }
                            } catch (Exception ex) {
                                System.out.println("Notif : " + ex);
                            } finally {
                                if (rs != null) {
                                    rs.close();
                                }
                                if (ps != null) {
                                    ps.close();
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Notif : " + ex);
                        }
                        KdDPJP.requestFocus();
                    } else {
                        KdDPJPLayanan.setText(dpjp.getTable().getValueAt(dpjp.getTable().getSelectedRow(), 1).toString());
                        NmDPJPLayanan.setText(dpjp.getTable().getValueAt(dpjp.getTable().getSelectedRow(), 2).toString());
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        dpjp.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    dpjp.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        propinsikll.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (propinsikll.getTable().getSelectedRow() != -1) {
                    KdPropinsi.setText(propinsikll.getTable().getValueAt(propinsikll.getTable().getSelectedRow(), 1).toString());
                    NmPropinsi.setText(propinsikll.getTable().getValueAt(propinsikll.getTable().getSelectedRow(), 2).toString());
                    KdPropinsi.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        propinsikll.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    propinsikll.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kabupatenkll.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kabupatenkll.getTable().getSelectedRow() != -1) {
                    KdKabupaten.setText(kabupatenkll.getTable().getValueAt(kabupatenkll.getTable().getSelectedRow(), 1).toString());
                    NmKabupaten.setText(kabupatenkll.getTable().getValueAt(kabupatenkll.getTable().getSelectedRow(), 2).toString());
                    KdKabupaten.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        kabupatenkll.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    kabupatenkll.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kecamatankll.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kecamatankll.getTable().getSelectedRow() != -1) {
                    KdKecamatan.setText(kecamatankll.getTable().getValueAt(kecamatankll.getTable().getSelectedRow(), 1).toString());
                    NmKecamatan.setText(kecamatankll.getTable().getValueAt(kecamatankll.getTable().getSelectedRow(), 2).toString());
                    KdKecamatan.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        kecamatankll.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    kecamatankll.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        skdp.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    skdp.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        skdp2.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    skdp2.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        NoKartu.setDocument(new batasInput((int) 100).getKata(NoKartu));
        TNo.setDocument(new batasInput((byte) 15).getKata(TNo));
        TNm.setDocument(new batasInput((byte) 40).getKata(TNm));
        NmIbu.setDocument(new batasInput((byte) 40).getKata(NmIbu));
        TKtp.setDocument(new batasInput((byte) 20).getKata(TKtp));
        Kdpnj.setDocument(new batasInput((byte) 3).getKata(Kdpnj));
        TTlp.setDocument(new batasInput((byte) 13).getOnlyAngka(TTlp));
        TTmp.setDocument(new batasInput((byte) 15).getKata(TTmp));
        Alamat.setDocument(new batasInput((int) 200).getFilter(Alamat));
        AlamatPj.setDocument(new batasInput((int) 100).getFilter(AlamatPj));
        Pekerjaan.setDocument(new batasInput((byte) 15).getKata(Pekerjaan));
        PekerjaanPj.setDocument(new batasInput((byte) 15).getKata(PekerjaanPj));
        TUmur.setDocument(new batasInput((byte) 10).getKata(TUmur));
        Saudara.setDocument(new batasInput((byte) 50).getKata(Saudara));
        Kabupaten.setDocument(new batasInput((byte) 60).getFilter(Kabupaten));
        Kecamatan.setDocument(new batasInput((byte) 60).getFilter(Kecamatan));
        Kelurahan.setDocument(new batasInput((byte) 60).getFilter(Kelurahan));
        KabupatenPj.setDocument(new batasInput((byte) 60).getFilter(KabupatenPj));
        KecamatanPj.setDocument(new batasInput((byte) 60).getFilter(KecamatanPj));
        KelurahanPj.setDocument(new batasInput((byte) 60).getFilter(KelurahanPj));
        TNoPeserta.setDocument(new batasInput((byte) 25).getKata(TNoPeserta));
        TNoReg.setDocument(new batasInput((byte) 8).getKata(TNoReg));
        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        kddokter.setDocument(new batasInput((byte) 20).getKata(kddokter));
        NoKartu.setDocument(new batasInput((byte) 20).getKata(NoKartu));
        Catatan.setDocument(new batasInput((byte) 50).getKata(Catatan));
        Keterangan.setDocument(new batasInput((byte) 50).getKata(Keterangan));
        NoSEPSuplesi.setDocument(new batasInput((byte) 40).getKata(NoSEPSuplesi));
        Propinsi.setDocument(new batasInput((byte) 30).getFilter(Propinsi));
        PropinsiPj.setDocument(new batasInput((byte) 30).getFilter(PropinsiPj));
        EMail.setDocument(new batasInput((byte) 50).getFilter(EMail));
        NIP.setDocument(new batasInput((byte) 30).getFilter(NIP));
        PenanggungJawab.setDocument(new batasInput((byte) 100).getKata(PenanggungJawab));
        try {
            KdPPK.setText(Sequel.cariIsi("select kode_ppk from setting"));
            NmPPK.setText(Sequel.cariIsi("select nama_instansi from setting"));
            pssetalamat = koneksi.prepareStatement("select * from set_alamat_pasien");
            try {
                rs = pssetalamat.executeQuery();
                while (rs.next()) {
                    Kelurahan.setEditable(rs.getBoolean("kelurahan"));
                    KelurahanPj.setEditable(rs.getBoolean("kelurahan"));
                    Kecamatan.setEditable(rs.getBoolean("kecamatan"));
                    KecamatanPj.setEditable(rs.getBoolean("kecamatan"));
                    Kabupaten.setEditable(rs.getBoolean("kabupaten"));
                    KabupatenPj.setEditable(rs.getBoolean("kabupaten"));
                    Propinsi.setEditable(rs.getBoolean("propinsi"));
                    PropinsiPj.setEditable(rs.getBoolean("propinsi"));
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pssetalamat != null) {
                    pssetalamat.close();
                }
            }

            pskelengkapan = koneksi.prepareStatement("select * from set_kelengkapan_data_pasien");
            try {
                rs = pskelengkapan.executeQuery();
                while (rs.next()) {
                    no_ktp = rs.getString("no_ktp");
                    p_no_ktp = rs.getInt("p_no_ktp");
                    tmp_lahir = rs.getString("tmp_lahir");
                    p_tmp_lahir = rs.getInt("p_tmp_lahir");
                    nm_ibu = rs.getString("nm_ibu");
                    p_nm_ibu = rs.getInt("p_nm_ibu");
                    alamat = rs.getString("alamat");
                    p_alamat = rs.getInt("p_alamat");
                    pekerjaan = rs.getString("pekerjaan");
                    p_pekerjaan = rs.getInt("p_pekerjaan");
                    no_tlp = rs.getString("no_tlp");
                    p_no_tlp = rs.getInt("p_no_tlp");
                    umur = rs.getString("umur");
                    p_umur = rs.getInt("p_umur");
                    namakeluarga = rs.getString("namakeluarga");
                    p_namakeluarga = rs.getInt("p_namakeluarga");
                    no_peserta = rs.getString("no_peserta");
                    p_no_peserta = rs.getInt("p_no_peserta");
                    kelurahan = rs.getString("kelurahan");
                    p_kelurahan = rs.getInt("p_kelurahan");
                    kecamatan = rs.getString("kecamatan");
                    p_kecamatan = rs.getInt("p_kecamatan");
                    kabupaten = rs.getString("kabupaten");
                    p_kabupaten = rs.getInt("p_kabupaten");
                    pekerjaanpj = rs.getString("pekerjaanpj");
                    p_pekerjaanpj = rs.getInt("p_pekerjaanpj");
                    alamatpj = rs.getString("alamatpj");
                    p_alamatpj = rs.getInt("p_alamatpj");
                    kelurahanpj = rs.getString("kelurahanpj");
                    p_kelurahanpj = rs.getInt("p_kelurahanpj");
                    kecamatanpj = rs.getString("kecamatanpj");
                    p_kecamatanpj = rs.getInt("p_kecamatanpj");
                    kabupatenpj = rs.getString("kabupatenpj");
                    p_kabupatenpj = rs.getInt("p_kabupatenpj");
                    propinsi = rs.getString("propinsi");
                    p_propinsi = rs.getInt("p_propinsi");
                    propinsipj = rs.getString("propinsipj");
                    p_propinsipj = rs.getInt("p_propinsipj");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pskelengkapan != null) {
                    pskelengkapan.close();
                }
            }

            ps = koneksi.prepareStatement("select * from set_urut_no_rkm_medis");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    pengurutan = rs.getString("urutan");
                    tahun = rs.getString("tahun");
                    bulan = rs.getString("bulan");
                    posisitahun = rs.getString("posisi_tahun_bulan");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        hariawal = Sequel.cariIsi("select hariawal from set_jam_minimal");

        try {
            user = akses.getkode().replace(" ", "").substring(0, 9);
        } catch (Exception e) {
            user = akses.getkode();
        }
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            link = prop.getProperty("URLAPIBPJS");
            URUTNOREG = prop.getProperty("URUTNOREG");
            BASENOREG = prop.getProperty("BASENOREG");
        } catch (Exception e) {
            URUTNOREG = "";
            BASENOREG = "";
            System.out.println("E : " + e);
        }

        if (tampilkantni.equals("Yes")) {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CmbJk = new widget.ComboBox();
        DTPLahir = new widget.Tanggal();
        TUmur = new widget.TextBox();
        TNoPeserta = new widget.TextBox();
        TKtp = new widget.TextBox();
        NoRm = new widget.TextBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        kdpoli = new widget.TextBox();
        TPoli = new widget.TextBox();
        TBiaya = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDocument = new javax.swing.JMenuItem();
        ppPengajuan = new javax.swing.JMenuItem();
        ppPengajuan1 = new javax.swing.JMenuItem();
        ppPengajuan2 = new javax.swing.JMenuItem();
        ppPengajuan3 = new javax.swing.JMenuItem();
        ppStatusFinger = new javax.swing.JMenuItem();
        NoBalasan = new widget.TextBox();
        kdsuku = new widget.TextBox();
        kdbahasa = new widget.TextBox();
        kdgolongantni = new widget.TextBox();
        kdsatuantni = new widget.TextBox();
        kdpangkattni = new widget.TextBox();
        kdjabatantni = new widget.TextBox();
        kdgolonganpolri = new widget.TextBox();
        kdsatuanpolri = new widget.TextBox();
        kdpangkatpolri = new widget.TextBox();
        kdjabatanpolri = new widget.TextBox();
        kdperusahaan = new widget.TextBox();
        kdcacat = new widget.TextBox();
        DlgSEP = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        PanelInput = new javax.swing.JPanel();
        scrollPane2 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        FormKelengkapanPasien = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TTmp = new widget.TextBox();
        CMbGd = new widget.ComboBox();
        jLabel9 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel18 = new widget.Label();
        cmbAgama = new widget.ComboBox();
        jLabel19 = new widget.Label();
        CmbStts = new widget.ComboBox();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        jLabel12 = new widget.Label();
        Alamat = new widget.TextBox();
        TTlp = new widget.TextBox();
        TNo = new widget.TextBox();
        DTPDaftar = new widget.Tanggal();
        jLabel22 = new widget.Label();
        jLabel24 = new widget.Label();
        CMbPnd = new widget.ComboBox();
        Saudara = new widget.TextBox();
        R5 = new widget.RadioButton();
        R4 = new widget.RadioButton();
        R3 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        R1 = new widget.RadioButton();
        jLabel25 = new widget.Label();
        Kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        BtnPenjab = new widget.Button();
        Kelurahan = new widget.TextBox();
        Kecamatan = new widget.TextBox();
        Kabupaten = new widget.TextBox();
        BtnKelurahan = new widget.Button();
        BtnKecamatan = new widget.Button();
        BtnKabupaten = new widget.Button();
        jLabel14 = new widget.Label();
        NmIbu = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        PekerjaanPj = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel29 = new widget.Label();
        AlamatPj = new widget.TextBox();
        KecamatanPj = new widget.TextBox();
        BtnKecamatanPj = new widget.Button();
        KabupatenPj = new widget.TextBox();
        BtnKabupatenPj = new widget.Button();
        BtnKelurahanPj = new widget.Button();
        KelurahanPj = new widget.TextBox();
        ChkRM = new widget.CekBox();
        R6 = new widget.RadioButton();
        jLabel40 = new widget.Label();
        nmsukubangsa = new widget.TextBox();
        BtnSuku = new widget.Button();
        jLabel41 = new widget.Label();
        nmbahasa = new widget.TextBox();
        BtnBahasa = new widget.Button();
        jLabel42 = new widget.Label();
        nmcacat = new widget.TextBox();
        BtnCacat = new widget.Button();
        jLabel43 = new widget.Label();
        nmperusahaan = new widget.TextBox();
        BtnPerusahaan = new widget.Button();
        jLabel44 = new widget.Label();
        NIP = new widget.TextBox();
        jLabel45 = new widget.Label();
        EMail = new widget.TextBox();
        Propinsi = new widget.TextBox();
        BtnPropinsi = new widget.Button();
        PropinsiPj = new widget.TextBox();
        btnPropinsiPj = new widget.Button();
        TNm = new widget.TextBox();
        FormKelengkapanSEP = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        jLabel10 = new widget.Label();
        KdPPK = new widget.TextBox();
        NmPPK = new widget.TextBox();
        jLabel11 = new widget.Label();
        KdPpkRujukan = new widget.TextBox();
        NmPpkRujukan = new widget.TextBox();
        jLabel15 = new widget.Label();
        KdPenyakit = new widget.TextBox();
        NmPenyakit = new widget.TextBox();
        NmPoli = new widget.TextBox();
        KdPoli = new widget.TextBox();
        LabelPoli = new widget.Label();
        jLabel5 = new widget.Label();
        TNoReg = new widget.TextBox();
        jLabel37 = new widget.Label();
        LabelPoli2 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        AsalRujukan = new widget.ComboBox();
        jLabel23 = new widget.Label();
        TanggalSEP = new widget.Tanggal();
        jLabel30 = new widget.Label();
        TanggalRujuk = new widget.Tanggal();
        jLabel31 = new widget.Label();
        NoRujukan = new widget.TextBox();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        Catatan = new widget.TextBox();
        JenisPelayanan = new widget.ComboBox();
        LabelKelas = new widget.Label();
        Kelas = new widget.ComboBox();
        jLabel38 = new widget.Label();
        Eksekutif = new widget.ComboBox();
        LabelKelas1 = new widget.Label();
        COB = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Katarak = new widget.ComboBox();
        jLabel35 = new widget.Label();
        NaikKelas = new widget.ComboBox();
        Pembiayaan = new widget.ComboBox();
        jLabel52 = new widget.Label();
        PenanggungJawab = new widget.TextBox();
        LabelKelas2 = new widget.Label();
        jLabel36 = new widget.Label();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel48 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel49 = new widget.Label();
        Suplesi = new widget.ComboBox();
        jLabel50 = new widget.Label();
        NoSEPSuplesi = new widget.TextBox();
        LabelPoli3 = new widget.Label();
        KdPropinsi = new widget.TextBox();
        NmPropinsi = new widget.TextBox();
        btnPropinsi = new widget.Button();
        btnKabupaten = new widget.Button();
        NmKabupaten = new widget.TextBox();
        KdKabupaten = new widget.TextBox();
        LabelPoli4 = new widget.Label();
        LabelPoli5 = new widget.Label();
        KdKecamatan = new widget.TextBox();
        NmKecamatan = new widget.TextBox();
        btnKecamatan = new widget.Button();
        jLabel54 = new widget.Label();
        TujuanKunjungan = new widget.ComboBox();
        jLabel55 = new widget.Label();
        Penunjang = new widget.ComboBox();
        FlagProsedur = new widget.ComboBox();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        LabelPoli6 = new widget.Label();
        KdDPJPLayanan = new widget.TextBox();
        NmDPJPLayanan = new widget.TextBox();
        btnDPJPLayanan = new widget.Button();
        jLabel34 = new widget.Label();
        LakaLantas = new widget.ComboBox();
        jLabel47 = new widget.Label();
        TanggalKKL = new widget.Tanggal();
        AsesmenPoli = new widget.ComboBox();
        panelGlass6 = new widget.panelisi();
        jLabel16 = new widget.Label();
        NoKartu = new widget.TextBox();
        btnPasien = new widget.Button();
        BtnCari = new widget.Button();
        jLabel17 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnCari1 = new widget.Button();
        BtnKeluar = new widget.Button();

        CmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "L", "P" }));
        CmbJk.setName("CmbJk"); // NOI18N
        CmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJkKeyPressed(evt);
            }
        });

        DTPLahir.setEditable(false);
        DTPLahir.setForeground(new java.awt.Color(50, 70, 50));
        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-03-2023" }));
        DTPLahir.setDisplayFormat("dd-MM-yyyy");
        DTPLahir.setName("DTPLahir"); // NOI18N
        DTPLahir.setOpaque(false);
        DTPLahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPLahirItemStateChanged(evt);
            }
        });
        DTPLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPLahirKeyPressed(evt);
            }
        });

        TUmur.setName("TUmur"); // NOI18N
        TUmur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TUmurKeyPressed(evt);
            }
        });

        TNoPeserta.setHighlighter(null);
        TNoPeserta.setName("TNoPeserta"); // NOI18N
        TNoPeserta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPesertaKeyPressed(evt);
            }
        });

        TKtp.setName("TKtp"); // NOI18N
        TKtp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtpKeyPressed(evt);
            }
        });

        NoRm.setHighlighter(null);
        NoRm.setName("NoRm"); // NOI18N
        NoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmKeyPressed(evt);
            }
        });

        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });

        TPoli.setEditable(false);
        TPoli.setName("TPoli"); // NOI18N

        TBiaya.setText("0");
        TBiaya.setName("TBiaya"); // NOI18N
        TBiaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBiayaKeyPressed(evt);
            }
        });

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDocument.setBackground(new java.awt.Color(255, 255, 254));
        MnDocument.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDocument.setForeground(new java.awt.Color(50, 50, 50));
        MnDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDocument.setText("Cetak Document");
        MnDocument.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDocument.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDocument.setName("MnDocument"); // NOI18N
        MnDocument.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDocumentActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDocument);

        ppPengajuan.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan.setText("Pengajuan SEP Backdate");
        ppPengajuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan.setName("ppPengajuan"); // NOI18N
        ppPengajuan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuanBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPengajuan);

        ppPengajuan1.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan1.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan1.setText("Aproval SEP Backdate");
        ppPengajuan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan1.setName("ppPengajuan1"); // NOI18N
        ppPengajuan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuan1BtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPengajuan1);

        ppPengajuan2.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan2.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan2.setText("Pengajuan SEP Finger");
        ppPengajuan2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan2.setName("ppPengajuan2"); // NOI18N
        ppPengajuan2.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuan2BtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPengajuan2);

        ppPengajuan3.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuan3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan3.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan3.setText("Aproval SEP Finger");
        ppPengajuan3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan3.setName("ppPengajuan3"); // NOI18N
        ppPengajuan3.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuan3BtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPengajuan3);

        ppStatusFinger.setBackground(new java.awt.Color(255, 255, 254));
        ppStatusFinger.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStatusFinger.setForeground(new java.awt.Color(50, 50, 50));
        ppStatusFinger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStatusFinger.setText("Status Finger");
        ppStatusFinger.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStatusFinger.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStatusFinger.setName("ppStatusFinger"); // NOI18N
        ppStatusFinger.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStatusFinger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStatusFingerBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppStatusFinger);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        kdsuku.setName("kdsuku"); // NOI18N
        kdsuku.setPreferredSize(new java.awt.Dimension(207, 23));

        kdbahasa.setName("kdbahasa"); // NOI18N
        kdbahasa.setPreferredSize(new java.awt.Dimension(207, 23));

        kdgolongantni.setName("kdgolongantni"); // NOI18N
        kdgolongantni.setPreferredSize(new java.awt.Dimension(207, 23));

        kdsatuantni.setName("kdsatuantni"); // NOI18N
        kdsatuantni.setPreferredSize(new java.awt.Dimension(207, 23));

        kdpangkattni.setName("kdpangkattni"); // NOI18N
        kdpangkattni.setPreferredSize(new java.awt.Dimension(207, 23));

        kdjabatantni.setName("kdjabatantni"); // NOI18N
        kdjabatantni.setPreferredSize(new java.awt.Dimension(207, 23));

        kdgolonganpolri.setName("kdgolonganpolri"); // NOI18N
        kdgolonganpolri.setPreferredSize(new java.awt.Dimension(207, 23));

        kdsatuanpolri.setName("kdsatuanpolri"); // NOI18N
        kdsatuanpolri.setPreferredSize(new java.awt.Dimension(207, 23));

        kdpangkatpolri.setName("kdpangkatpolri"); // NOI18N
        kdpangkatpolri.setPreferredSize(new java.awt.Dimension(207, 23));

        kdjabatanpolri.setName("kdjabatanpolri"); // NOI18N
        kdjabatanpolri.setPreferredSize(new java.awt.Dimension(207, 23));

        kdperusahaan.setEditable(false);
        kdperusahaan.setHighlighter(null);
        kdperusahaan.setName("kdperusahaan"); // NOI18N
        kdperusahaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdperusahaanKeyPressed(evt);
            }
        });

        kdcacat.setEditable(false);
        kdcacat.setHighlighter(null);
        kdcacat.setName("kdcacat"); // NOI18N
        kdcacat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdcacatKeyPressed(evt);
            }
        });

        DlgSEP.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSEP.setName("DlgSEP"); // NOI18N
        DlgSEP.setUndecorated(true);
        DlgSEP.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Keterangan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));
        DlgSEP.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(200, 877));
        PanelInput.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrollPane2.setBorder(null);
        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setPreferredSize(new java.awt.Dimension(1093, 138));

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(560, 168));

        FormKelengkapanPasien.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), "::[ Kelengkapan Data Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormKelengkapanPasien.setName("FormKelengkapanPasien"); // NOI18N
        FormKelengkapanPasien.setOpaque(false);
        FormKelengkapanPasien.setPreferredSize(new java.awt.Dimension(1000, 485));
        FormKelengkapanPasien.setLayout(null);

        jLabel3.setText("No.Rekam Medis :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormKelengkapanPasien.add(jLabel3);
        jLabel3.setBounds(5, 25, 100, 23);

        TTmp.setName("TTmp"); // NOI18N
        TTmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTmpKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(TTmp);
        TTmp.setBounds(109, 55, 120, 23);

        CMbGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "A", "B", "AB", "O" }));
        CMbGd.setName("CMbGd"); // NOI18N
        CMbGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbGdKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CMbGd);
        CMbGd.setBounds(347, 55, 90, 23);

        jLabel9.setText("G.Darah :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormKelengkapanPasien.add(jLabel9);
        jLabel9.setBounds(258, 55, 85, 23);

        jLabel13.setText("Tempat Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormKelengkapanPasien.add(jLabel13);
        jLabel13.setBounds(5, 55, 100, 23);

        jLabel18.setText("Agama :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormKelengkapanPasien.add(jLabel18);
        jLabel18.setBounds(5, 85, 100, 23);

        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISLAM", "KRISTEN", "KATOLIK", "HINDU", "BUDHA", "KONG HU CHU", "-" }));
        cmbAgama.setLightWeightPopupEnabled(false);
        cmbAgama.setName("cmbAgama"); // NOI18N
        cmbAgama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAgamaKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(cmbAgama);
        cmbAgama.setBounds(109, 85, 120, 23);

        jLabel19.setText("Stts. Nikah :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormKelengkapanPasien.add(jLabel19);
        jLabel19.setBounds(230, 85, 65, 23);

        CmbStts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MENIKAH", "BELUM MENIKAH", "JANDA", "DUDHA" }));
        CmbStts.setLightWeightPopupEnabled(false);
        CmbStts.setName("CmbStts"); // NOI18N
        CmbStts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbSttsKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CmbStts);
        CmbStts.setBounds(299, 85, 138, 23);

        jLabel20.setText("Alamat Pasien :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormKelengkapanPasien.add(jLabel20);
        jLabel20.setBounds(432, 25, 100, 23);

        jLabel21.setText("No.Telp :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormKelengkapanPasien.add(jLabel21);
        jLabel21.setBounds(5, 145, 100, 23);

        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Pekerjaan);
        Pekerjaan.setBounds(109, 115, 120, 23);

        jLabel12.setText("Pekerjaan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormKelengkapanPasien.add(jLabel12);
        jLabel12.setBounds(5, 115, 100, 23);

        Alamat.setText("ALAMAT");
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AlamatMouseMoved(evt);
            }
        });
        Alamat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AlamatMouseExited(evt);
            }
        });
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Alamat);
        Alamat.setBounds(536, 25, 375, 23);

        TTlp.setHighlighter(null);
        TTlp.setName("TTlp"); // NOI18N
        TTlp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTlpKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(TTlp);
        TTlp.setBounds(109, 145, 120, 23);

        TNo.setEditable(false);
        TNo.setBackground(new java.awt.Color(245, 250, 240));
        TNo.setHighlighter(null);
        TNo.setName("TNo"); // NOI18N
        TNo.setOpaque(true);
        TNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(TNo);
        TNo.setBounds(109, 25, 80, 23);

        DTPDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-03-2023" }));
        DTPDaftar.setDisplayFormat("dd-MM-yyyy");
        DTPDaftar.setName("DTPDaftar"); // NOI18N
        DTPDaftar.setOpaque(false);
        DTPDaftar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPDaftarKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(DTPDaftar);
        DTPDaftar.setBounds(347, 145, 90, 23);

        jLabel22.setText("Pertama Daftar :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormKelengkapanPasien.add(jLabel22);
        jLabel22.setBounds(258, 145, 85, 23);

        jLabel24.setText("Pendidikan :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormKelengkapanPasien.add(jLabel24);
        jLabel24.setBounds(258, 115, 85, 23);

        CMbPnd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "TS", "TK", "SD", "SMP", "SMA", "D1", "D2", "D3", "D4", "S1", "S2", "S3" }));
        CMbPnd.setName("CMbPnd"); // NOI18N
        CMbPnd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbPndKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(CMbPnd);
        CMbPnd.setBounds(347, 115, 90, 23);

        Saudara.setName("Saudara"); // NOI18N
        Saudara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaudaraKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Saudara);
        Saudara.setBounds(536, 145, 142, 23);

        R5.setSelected(true);
        R5.setText("Saudara");
        R5.setIconTextGap(0);
        R5.setName("R5"); // NOI18N
        FormKelengkapanPasien.add(R5);
        R5.setBounds(752, 115, 60, 23);

        R4.setText("Suami");
        R4.setIconTextGap(0);
        R4.setName("R4"); // NOI18N
        FormKelengkapanPasien.add(R4);
        R4.setBounds(692, 115, 50, 23);

        R3.setText("Istri");
        R3.setIconTextGap(0);
        R3.setName("R3"); // NOI18N
        FormKelengkapanPasien.add(R3);
        R3.setBounds(642, 115, 44, 23);

        R2.setText("Ibu");
        R2.setIconTextGap(0);
        R2.setName("R2"); // NOI18N
        FormKelengkapanPasien.add(R2);
        R2.setBounds(594, 115, 40, 23);

        R1.setText("Ayah");
        R1.setIconTextGap(0);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(40, 20));
        FormKelengkapanPasien.add(R1);
        R1.setBounds(536, 115, 46, 23);

        jLabel25.setText("Askes/Asuransi :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormKelengkapanPasien.add(jLabel25);
        jLabel25.setBounds(5, 205, 100, 23);

        Kdpnj.setText("-");
        Kdpnj.setHighlighter(null);
        Kdpnj.setName("Kdpnj"); // NOI18N
        Kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdpnjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Kdpnj);
        Kdpnj.setBounds(109, 205, 80, 23);

        nmpnj.setEditable(false);
        nmpnj.setText("-");
        nmpnj.setName("nmpnj"); // NOI18N
        FormKelengkapanPasien.add(nmpnj);
        nmpnj.setBounds(191, 205, 216, 23);

        BtnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenjab.setMnemonic('1');
        BtnPenjab.setToolTipText("ALt+1");
        BtnPenjab.setName("BtnPenjab"); // NOI18N
        BtnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenjabActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnPenjab);
        BtnPenjab.setBounds(409, 205, 28, 23);

        Kelurahan.setText("KELURAHAN");
        Kelurahan.setHighlighter(null);
        Kelurahan.setName("Kelurahan"); // NOI18N
        Kelurahan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KelurahanMouseMoved(evt);
            }
        });
        Kelurahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KelurahanMouseExited(evt);
            }
        });
        Kelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Kelurahan);
        Kelurahan.setBounds(536, 55, 156, 23);

        Kecamatan.setText("KECAMATAN");
        Kecamatan.setHighlighter(null);
        Kecamatan.setName("Kecamatan"); // NOI18N
        Kecamatan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KecamatanMouseMoved(evt);
            }
        });
        Kecamatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KecamatanMouseExited(evt);
            }
        });
        Kecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Kecamatan);
        Kecamatan.setBounds(725, 55, 156, 23);

        Kabupaten.setText("KABUPATEN");
        Kabupaten.setHighlighter(null);
        Kabupaten.setName("Kabupaten"); // NOI18N
        Kabupaten.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KabupatenMouseMoved(evt);
            }
        });
        Kabupaten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KabupatenMouseExited(evt);
            }
        });
        Kabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Kabupaten);
        Kabupaten.setBounds(536, 85, 156, 23);

        BtnKelurahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahan.setMnemonic('2');
        BtnKelurahan.setToolTipText("ALt+2");
        BtnKelurahan.setName("BtnKelurahan"); // NOI18N
        BtnKelurahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKelurahan);
        BtnKelurahan.setBounds(694, 55, 28, 23);

        BtnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatan.setMnemonic('3');
        BtnKecamatan.setToolTipText("ALt+3");
        BtnKecamatan.setName("BtnKecamatan"); // NOI18N
        BtnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKecamatan);
        BtnKecamatan.setBounds(883, 55, 28, 23);

        BtnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupaten.setMnemonic('4');
        BtnKabupaten.setToolTipText("ALt+4");
        BtnKabupaten.setName("BtnKabupaten"); // NOI18N
        BtnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKabupaten);
        BtnKabupaten.setBounds(694, 85, 28, 23);

        jLabel14.setText("Nama Ibu :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormKelengkapanPasien.add(jLabel14);
        jLabel14.setBounds(5, 175, 100, 23);

        NmIbu.setName("NmIbu"); // NOI18N
        NmIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmIbuKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(NmIbu);
        NmIbu.setBounds(109, 175, 328, 23);

        jLabel26.setText("Png. Jawab :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormKelengkapanPasien.add(jLabel26);
        jLabel26.setBounds(432, 115, 100, 23);

        jLabel27.setText("Png. Jawab :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormKelengkapanPasien.add(jLabel27);
        jLabel27.setBounds(432, 145, 100, 23);

        PekerjaanPj.setName("PekerjaanPj"); // NOI18N
        PekerjaanPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(PekerjaanPj);
        PekerjaanPj.setBounds(769, 145, 142, 23);

        jLabel28.setText("Pekerjaan P.J. :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormKelengkapanPasien.add(jLabel28);
        jLabel28.setBounds(682, 145, 83, 23);

        jLabel29.setText("Alamat P.J. :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormKelengkapanPasien.add(jLabel29);
        jLabel29.setBounds(432, 175, 100, 23);

        AlamatPj.setText("ALAMAT");
        AlamatPj.setHighlighter(null);
        AlamatPj.setName("AlamatPj"); // NOI18N
        AlamatPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AlamatPjMouseMoved(evt);
            }
        });
        AlamatPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AlamatPjMouseExited(evt);
            }
        });
        AlamatPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(AlamatPj);
        AlamatPj.setBounds(536, 175, 375, 23);

        KecamatanPj.setText("KECAMATAN");
        KecamatanPj.setHighlighter(null);
        KecamatanPj.setName("KecamatanPj"); // NOI18N
        KecamatanPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KecamatanPjMouseMoved(evt);
            }
        });
        KecamatanPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KecamatanPjMouseExited(evt);
            }
        });
        KecamatanPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(KecamatanPj);
        KecamatanPj.setBounds(725, 205, 156, 23);

        BtnKecamatanPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatanPj.setMnemonic('3');
        BtnKecamatanPj.setToolTipText("ALt+3");
        BtnKecamatanPj.setName("BtnKecamatanPj"); // NOI18N
        BtnKecamatanPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanPjActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKecamatanPj);
        BtnKecamatanPj.setBounds(883, 205, 28, 23);

        KabupatenPj.setText("KABUPATEN");
        KabupatenPj.setHighlighter(null);
        KabupatenPj.setName("KabupatenPj"); // NOI18N
        KabupatenPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KabupatenPjMouseMoved(evt);
            }
        });
        KabupatenPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KabupatenPjMouseExited(evt);
            }
        });
        KabupatenPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(KabupatenPj);
        KabupatenPj.setBounds(536, 235, 156, 23);

        BtnKabupatenPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupatenPj.setMnemonic('4');
        BtnKabupatenPj.setToolTipText("ALt+4");
        BtnKabupatenPj.setName("BtnKabupatenPj"); // NOI18N
        BtnKabupatenPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenPjActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKabupatenPj);
        BtnKabupatenPj.setBounds(694, 235, 28, 23);

        BtnKelurahanPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahanPj.setMnemonic('2');
        BtnKelurahanPj.setToolTipText("ALt+2");
        BtnKelurahanPj.setName("BtnKelurahanPj"); // NOI18N
        BtnKelurahanPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanPjActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnKelurahanPj);
        BtnKelurahanPj.setBounds(694, 205, 28, 23);

        KelurahanPj.setText("KELURAHAN");
        KelurahanPj.setHighlighter(null);
        KelurahanPj.setName("KelurahanPj"); // NOI18N
        KelurahanPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KelurahanPjMouseMoved(evt);
            }
        });
        KelurahanPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KelurahanPjMouseExited(evt);
            }
        });
        KelurahanPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(KelurahanPj);
        KelurahanPj.setBounds(536, 205, 156, 23);

        ChkRM.setBorder(null);
        ChkRM.setSelected(true);
        ChkRM.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkRM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkRM.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkRM.setName("ChkRM"); // NOI18N
        ChkRM.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRMItemStateChanged(evt);
            }
        });
        FormKelengkapanPasien.add(ChkRM);
        ChkRM.setBounds(410, 30, 23, 23);

        R6.setText("Anak");
        R6.setIconTextGap(0);
        R6.setName("R6"); // NOI18N
        FormKelengkapanPasien.add(R6);
        R6.setBounds(827, 115, 60, 23);

        jLabel40.setText("Suku/Bangsa :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormKelengkapanPasien.add(jLabel40);
        jLabel40.setBounds(5, 235, 100, 23);

        nmsukubangsa.setEditable(false);
        nmsukubangsa.setName("nmsukubangsa"); // NOI18N
        FormKelengkapanPasien.add(nmsukubangsa);
        nmsukubangsa.setBounds(109, 235, 298, 23);

        BtnSuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSuku.setMnemonic('1');
        BtnSuku.setToolTipText("ALt+1");
        BtnSuku.setName("BtnSuku"); // NOI18N
        BtnSuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSukuActionPerformed(evt);
            }
        });
        BtnSuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSukuKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnSuku);
        BtnSuku.setBounds(409, 235, 28, 23);

        jLabel41.setText("Bahasa :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormKelengkapanPasien.add(jLabel41);
        jLabel41.setBounds(5, 265, 100, 23);

        nmbahasa.setEditable(false);
        nmbahasa.setName("nmbahasa"); // NOI18N
        FormKelengkapanPasien.add(nmbahasa);
        nmbahasa.setBounds(109, 265, 298, 23);

        BtnBahasa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnBahasa.setMnemonic('1');
        BtnBahasa.setToolTipText("ALt+1");
        BtnBahasa.setName("BtnBahasa"); // NOI18N
        BtnBahasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBahasaActionPerformed(evt);
            }
        });
        BtnBahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBahasaKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnBahasa);
        BtnBahasa.setBounds(409, 265, 28, 23);

        jLabel42.setText("Cacat Fisik :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormKelengkapanPasien.add(jLabel42);
        jLabel42.setBounds(5, 295, 100, 23);

        nmcacat.setEditable(false);
        nmcacat.setName("nmcacat"); // NOI18N
        FormKelengkapanPasien.add(nmcacat);
        nmcacat.setBounds(109, 295, 298, 23);

        BtnCacat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCacat.setMnemonic('1');
        BtnCacat.setToolTipText("ALt+1");
        BtnCacat.setName("BtnCacat"); // NOI18N
        BtnCacat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCacatActionPerformed(evt);
            }
        });
        BtnCacat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCacatKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnCacat);
        BtnCacat.setBounds(409, 295, 28, 23);

        jLabel43.setText("Instansi Pasien :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormKelengkapanPasien.add(jLabel43);
        jLabel43.setBounds(432, 265, 100, 23);

        nmperusahaan.setEditable(false);
        nmperusahaan.setName("nmperusahaan"); // NOI18N
        FormKelengkapanPasien.add(nmperusahaan);
        nmperusahaan.setBounds(536, 265, 340, 23);

        BtnPerusahaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPerusahaan.setMnemonic('1');
        BtnPerusahaan.setToolTipText("ALt+1");
        BtnPerusahaan.setName("BtnPerusahaan"); // NOI18N
        BtnPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerusahaanActionPerformed(evt);
            }
        });
        BtnPerusahaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerusahaanKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnPerusahaan);
        BtnPerusahaan.setBounds(882, 265, 28, 23);

        jLabel44.setText("NIP/NRP :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormKelengkapanPasien.add(jLabel44);
        jLabel44.setBounds(692, 295, 84, 23);

        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(NIP);
        NIP.setBounds(780, 295, 130, 23);

        jLabel45.setText("Email :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormKelengkapanPasien.add(jLabel45);
        jLabel45.setBounds(432, 295, 100, 23);

        EMail.setHighlighter(null);
        EMail.setName("EMail"); // NOI18N
        EMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EMailKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(EMail);
        EMail.setBounds(536, 295, 161, 23);

        Propinsi.setText("PROPINSI");
        Propinsi.setHighlighter(null);
        Propinsi.setName("Propinsi"); // NOI18N
        Propinsi.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PropinsiMouseMoved(evt);
            }
        });
        Propinsi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PropinsiMouseExited(evt);
            }
        });
        Propinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PropinsiKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(Propinsi);
        Propinsi.setBounds(725, 85, 156, 23);

        BtnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPropinsi.setMnemonic('4');
        BtnPropinsi.setToolTipText("ALt+4");
        BtnPropinsi.setName("BtnPropinsi"); // NOI18N
        BtnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPropinsiActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(BtnPropinsi);
        BtnPropinsi.setBounds(883, 85, 28, 23);

        PropinsiPj.setText("PROPINSI");
        PropinsiPj.setHighlighter(null);
        PropinsiPj.setName("PropinsiPj"); // NOI18N
        PropinsiPj.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PropinsiPjMouseMoved(evt);
            }
        });
        PropinsiPj.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PropinsiPjMouseExited(evt);
            }
        });
        PropinsiPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PropinsiPjKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(PropinsiPj);
        PropinsiPj.setBounds(725, 235, 156, 23);

        btnPropinsiPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPropinsiPj.setMnemonic('4');
        btnPropinsiPj.setToolTipText("ALt+4");
        btnPropinsiPj.setName("btnPropinsiPj"); // NOI18N
        btnPropinsiPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropinsiPjActionPerformed(evt);
            }
        });
        FormKelengkapanPasien.add(btnPropinsiPj);
        btnPropinsiPj.setBounds(883, 235, 28, 23);

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        FormKelengkapanPasien.add(TNm);
        TNm.setBounds(190, 20, 200, 30);

        FormKelengkapanSEP.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), "::[ Kelengkapan Data SEP, Registrasi & Kamar Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormKelengkapanSEP.setName("FormKelengkapanSEP"); // NOI18N
        FormKelengkapanSEP.setOpaque(false);
        FormKelengkapanSEP.setPreferredSize(new java.awt.Dimension(1000, 337));
        FormKelengkapanSEP.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormKelengkapanSEP.add(jLabel4);
        jLabel4.setBounds(3, 25, 100, 23);

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormKelengkapanSEP.add(TNoRw);
        TNoRw.setBounds(107, 25, 190, 23);

        jLabel10.setText("PPK Pelayanan :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormKelengkapanSEP.add(jLabel10);
        jLabel10.setBounds(3, 55, 100, 23);

        KdPPK.setEditable(false);
        KdPPK.setBackground(new java.awt.Color(245, 250, 240));
        KdPPK.setHighlighter(null);
        KdPPK.setName("KdPPK"); // NOI18N
        FormKelengkapanSEP.add(KdPPK);
        KdPPK.setBounds(107, 55, 70, 23);

        NmPPK.setEditable(false);
        NmPPK.setBackground(new java.awt.Color(245, 250, 240));
        NmPPK.setHighlighter(null);
        NmPPK.setName("NmPPK"); // NOI18N
        FormKelengkapanSEP.add(NmPPK);
        NmPPK.setBounds(179, 55, 306, 23);

        jLabel11.setText("PPK Rujukan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormKelengkapanSEP.add(jLabel11);
        jLabel11.setBounds(3, 115, 100, 23);

        KdPpkRujukan.setEditable(false);
        KdPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan.setHighlighter(null);
        KdPpkRujukan.setName("KdPpkRujukan"); // NOI18N
        FormKelengkapanSEP.add(KdPpkRujukan);
        KdPpkRujukan.setBounds(107, 115, 90, 23);

        NmPpkRujukan.setEditable(false);
        NmPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        NmPpkRujukan.setHighlighter(null);
        NmPpkRujukan.setName("NmPpkRujukan"); // NOI18N
        FormKelengkapanSEP.add(NmPpkRujukan);
        NmPpkRujukan.setBounds(199, 115, 255, 23);

        jLabel15.setText("Diagnosa Awal :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormKelengkapanSEP.add(jLabel15);
        jLabel15.setBounds(3, 145, 100, 23);

        KdPenyakit.setEditable(false);
        KdPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit.setHighlighter(null);
        KdPenyakit.setName("KdPenyakit"); // NOI18N
        FormKelengkapanSEP.add(KdPenyakit);
        KdPenyakit.setBounds(107, 145, 90, 23);

        NmPenyakit.setEditable(false);
        NmPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit.setHighlighter(null);
        NmPenyakit.setName("NmPenyakit"); // NOI18N
        FormKelengkapanSEP.add(NmPenyakit);
        NmPenyakit.setBounds(199, 145, 255, 23);

        NmPoli.setEditable(false);
        NmPoli.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormKelengkapanSEP.add(NmPoli);
        NmPoli.setBounds(199, 175, 255, 23);

        KdPoli.setEditable(false);
        KdPoli.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormKelengkapanSEP.add(KdPoli);
        KdPoli.setBounds(107, 175, 90, 23);

        LabelPoli.setText("Poli Tujuan :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli);
        LabelPoli.setBounds(3, 175, 100, 23);

        jLabel5.setText("No. Reg. :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormKelengkapanSEP.add(jLabel5);
        jLabel5.setBounds(331, 25, 70, 23);

        TNoReg.setHighlighter(null);
        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRegKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TNoReg);
        TNoReg.setBounds(405, 25, 80, 23);

        jLabel37.setText("Asal Rujukan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormKelengkapanSEP.add(jLabel37);
        jLabel37.setBounds(3, 85, 100, 23);

        LabelPoli2.setText("DPJP SEP :");
        LabelPoli2.setName("LabelPoli2"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli2);
        LabelPoli2.setBounds(3, 205, 100, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setBackground(new java.awt.Color(245, 250, 240));
        KdDPJP.setHighlighter(null);
        KdDPJP.setName("KdDPJP"); // NOI18N
        FormKelengkapanSEP.add(KdDPJP);
        KdDPJP.setBounds(107, 205, 90, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setBackground(new java.awt.Color(245, 250, 240));
        NmDPJP.setHighlighter(null);
        NmDPJP.setName("NmDPJP"); // NOI18N
        FormKelengkapanSEP.add(NmDPJP);
        NmDPJP.setBounds(199, 205, 255, 23);

        AsalRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Faskes 1", "2. Faskes 2(RS)" }));
        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(AsalRujukan);
        AsalRujukan.setBounds(107, 85, 125, 23);

        jLabel23.setText("Tgl.SEP :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel23);
        jLabel23.setBounds(295, 355, 54, 23);

        TanggalSEP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-03-2023 14:00:15" }));
        TanggalSEP.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalSEP.setName("TanggalSEP"); // NOI18N
        TanggalSEP.setOpaque(false);
        TanggalSEP.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSEPKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TanggalSEP);
        TanggalSEP.setBounds(353, 355, 132, 23);

        jLabel30.setText("Rujuk :");
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel30);
        jLabel30.setBounds(341, 325, 50, 23);

        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-03-2023" }));
        TanggalRujuk.setDisplayFormat("dd-MM-yyyy");
        TanggalRujuk.setName("TanggalRujuk"); // NOI18N
        TanggalRujuk.setOpaque(false);
        TanggalRujuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujukKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TanggalRujuk);
        TanggalRujuk.setBounds(395, 325, 90, 23);

        jLabel31.setText("No.Rujuk :");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel31);
        jLabel31.setBounds(3, 355, 100, 23);

        NoRujukan.setHighlighter(null);
        NoRujukan.setName("NoRujukan"); // NOI18N
        NoRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(NoRujukan);
        NoRujukan.setBounds(107, 355, 180, 23);

        jLabel32.setText("Jns.Pelayanan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormKelengkapanSEP.add(jLabel32);
        jLabel32.setBounds(3, 235, 100, 23);

        jLabel33.setText("Catatan :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormKelengkapanSEP.add(jLabel33);
        jLabel33.setBounds(3, 325, 100, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Catatan);
        Catatan.setBounds(107, 325, 231, 23);

        JenisPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Ranap", "2. Ralan" }));
        JenisPelayanan.setSelectedIndex(1);
        JenisPelayanan.setName("JenisPelayanan"); // NOI18N
        JenisPelayanan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisPelayananItemStateChanged(evt);
            }
        });
        JenisPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPelayananKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(JenisPelayanan);
        JenisPelayanan.setBounds(107, 235, 93, 23);

        LabelKelas.setText("P.J.Naik Kelas :");
        LabelKelas.setName("LabelKelas"); // NOI18N
        FormKelengkapanSEP.add(LabelKelas);
        LabelKelas.setBounds(241, 265, 80, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Kelas 1", "2. Kelas 2", "3. kelas 3" }));
        Kelas.setSelectedIndex(2);
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Kelas);
        Kelas.setBounds(244, 235, 98, 23);

        jLabel38.setText("Eksekutif :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormKelengkapanSEP.add(jLabel38);
        jLabel38.setBounds(3, 295, 100, 23);

        Eksekutif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Eksekutif.setName("Eksekutif"); // NOI18N
        Eksekutif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EksekutifKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Eksekutif);
        Eksekutif.setBounds(107, 295, 93, 23);

        LabelKelas1.setText("COB :");
        LabelKelas1.setName("LabelKelas1"); // NOI18N
        FormKelengkapanSEP.add(LabelKelas1);
        LabelKelas1.setBounds(197, 295, 39, 23);

        COB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        COB.setName("COB"); // NOI18N
        COB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                COBKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(COB);
        COB.setBounds(240, 300, 98, 23);

        jLabel46.setText("Katarak :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormKelengkapanSEP.add(jLabel46);
        jLabel46.setBounds(341, 295, 50, 23);

        Katarak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Katarak.setName("Katarak"); // NOI18N
        Katarak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KatarakKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Katarak);
        Katarak.setBounds(395, 295, 90, 23);

        jLabel35.setText("Naik :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormKelengkapanSEP.add(jLabel35);
        jLabel35.setBounds(343, 235, 40, 23);

        NaikKelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. VVIP", "2. VIP", "3. Kelas I", "4. Kelas II", "5. Kelas III", "6. ICCU", "7. ICU" }));
        NaikKelas.setEnabled(false);
        NaikKelas.setName("NaikKelas"); // NOI18N
        NaikKelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NaikKelasItemStateChanged(evt);
            }
        });
        NaikKelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NaikKelasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(NaikKelas);
        NaikKelas.setBounds(387, 235, 98, 23);

        Pembiayaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "1. Pribadi", "2. Pemberi Kerja", "3. Asuransi Lain" }));
        Pembiayaan.setEnabled(false);
        Pembiayaan.setName("Pembiayaan"); // NOI18N
        Pembiayaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PembiayaanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Pembiayaan);
        Pembiayaan.setBounds(107, 265, 130, 23);

        jLabel52.setText("Pembiayaan :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormKelengkapanSEP.add(jLabel52);
        jLabel52.setBounds(3, 265, 100, 23);

        PenanggungJawab.setEditable(false);
        PenanggungJawab.setBackground(new java.awt.Color(245, 250, 240));
        PenanggungJawab.setHighlighter(null);
        PenanggungJawab.setName("PenanggungJawab"); // NOI18N
        FormKelengkapanSEP.add(PenanggungJawab);
        PenanggungJawab.setBounds(325, 265, 160, 23);

        LabelKelas2.setText("Kelas :");
        LabelKelas2.setName("LabelKelas2"); // NOI18N
        FormKelengkapanSEP.add(LabelKelas2);
        LabelKelas2.setBounds(200, 235, 40, 23);

        jLabel36.setText("Dr Dituju/DPJP :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormKelengkapanSEP.add(jLabel36);
        jLabel36.setBounds(495, 205, 90, 23);

        kddokter.setEditable(false);
        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(kddokter);
        kddokter.setBounds(589, 205, 90, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormKelengkapanSEP.add(TDokter);
        TDokter.setBounds(681, 205, 197, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('3');
        BtnDokter.setToolTipText("ALt+3");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(BtnDokter);
        BtnDokter.setBounds(880, 205, 28, 23);

        jLabel48.setText("Keterangan :");
        jLabel48.setName("jLabel48"); // NOI18N
        jLabel48.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel48);
        jLabel48.setBounds(495, 55, 90, 23);

        Keterangan.setEditable(false);
        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Keterangan);
        Keterangan.setBounds(589, 55, 319, 23);

        jLabel49.setText("Suplesi :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormKelengkapanSEP.add(jLabel49);
        jLabel49.setBounds(495, 85, 90, 23);

        Suplesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Suplesi.setEnabled(false);
        Suplesi.setName("Suplesi"); // NOI18N
        Suplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuplesiKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Suplesi);
        Suplesi.setBounds(589, 85, 90, 23);

        jLabel50.setText("No.SEP Suplesi :");
        jLabel50.setName("jLabel50"); // NOI18N
        jLabel50.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel50);
        jLabel50.setBounds(680, 85, 85, 23);

        NoSEPSuplesi.setEditable(false);
        NoSEPSuplesi.setHighlighter(null);
        NoSEPSuplesi.setName("NoSEPSuplesi"); // NOI18N
        NoSEPSuplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSEPSuplesiKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(NoSEPSuplesi);
        NoSEPSuplesi.setBounds(768, 85, 140, 23);

        LabelPoli3.setText("Propinsi KLL :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli3);
        LabelPoli3.setBounds(495, 115, 90, 23);

        KdPropinsi.setEditable(false);
        KdPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        KdPropinsi.setHighlighter(null);
        KdPropinsi.setName("KdPropinsi"); // NOI18N
        FormKelengkapanSEP.add(KdPropinsi);
        KdPropinsi.setBounds(589, 115, 70, 23);

        NmPropinsi.setEditable(false);
        NmPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        NmPropinsi.setHighlighter(null);
        NmPropinsi.setName("NmPropinsi"); // NOI18N
        FormKelengkapanSEP.add(NmPropinsi);
        NmPropinsi.setBounds(661, 115, 217, 23);

        btnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPropinsi.setMnemonic('X');
        btnPropinsi.setToolTipText("Alt+X");
        btnPropinsi.setEnabled(false);
        btnPropinsi.setName("btnPropinsi"); // NOI18N
        btnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropinsiActionPerformed(evt);
            }
        });
        btnPropinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPropinsiKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnPropinsi);
        btnPropinsi.setBounds(880, 115, 28, 23);

        btnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKabupaten.setMnemonic('X');
        btnKabupaten.setToolTipText("Alt+X");
        btnKabupaten.setEnabled(false);
        btnKabupaten.setName("btnKabupaten"); // NOI18N
        btnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKabupatenActionPerformed(evt);
            }
        });
        btnKabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKabupatenKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnKabupaten);
        btnKabupaten.setBounds(880, 145, 28, 23);

        NmKabupaten.setEditable(false);
        NmKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        NmKabupaten.setHighlighter(null);
        NmKabupaten.setName("NmKabupaten"); // NOI18N
        FormKelengkapanSEP.add(NmKabupaten);
        NmKabupaten.setBounds(661, 145, 217, 23);

        KdKabupaten.setEditable(false);
        KdKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        KdKabupaten.setHighlighter(null);
        KdKabupaten.setName("KdKabupaten"); // NOI18N
        FormKelengkapanSEP.add(KdKabupaten);
        KdKabupaten.setBounds(589, 145, 70, 23);

        LabelPoli4.setText("Kabupaten KLL :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli4);
        LabelPoli4.setBounds(495, 145, 90, 23);

        LabelPoli5.setText("Kecamatan KLL :");
        LabelPoli5.setName("LabelPoli5"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli5);
        LabelPoli5.setBounds(495, 175, 90, 23);

        KdKecamatan.setEditable(false);
        KdKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        KdKecamatan.setHighlighter(null);
        KdKecamatan.setName("KdKecamatan"); // NOI18N
        FormKelengkapanSEP.add(KdKecamatan);
        KdKecamatan.setBounds(589, 175, 70, 23);

        NmKecamatan.setEditable(false);
        NmKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        NmKecamatan.setHighlighter(null);
        NmKecamatan.setName("NmKecamatan"); // NOI18N
        FormKelengkapanSEP.add(NmKecamatan);
        NmKecamatan.setBounds(661, 175, 217, 23);

        btnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKecamatan.setMnemonic('X');
        btnKecamatan.setToolTipText("Alt+X");
        btnKecamatan.setEnabled(false);
        btnKecamatan.setName("btnKecamatan"); // NOI18N
        btnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKecamatanActionPerformed(evt);
            }
        });
        btnKecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKecamatanKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnKecamatan);
        btnKecamatan.setBounds(880, 175, 28, 23);

        jLabel54.setText("Tujuan :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormKelengkapanSEP.add(jLabel54);
        jLabel54.setBounds(495, 235, 90, 23);

        TujuanKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Normal", "1. Prosedur", "2. Konsul Dokter" }));
        TujuanKunjungan.setName("TujuanKunjungan"); // NOI18N
        TujuanKunjungan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TujuanKunjunganItemStateChanged(evt);
            }
        });
        TujuanKunjungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TujuanKunjunganKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TujuanKunjungan);
        TujuanKunjungan.setBounds(589, 235, 319, 23);

        jLabel55.setText("Penunjang :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormKelengkapanSEP.add(jLabel55);
        jLabel55.setBounds(495, 295, 90, 23);

        Penunjang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. Radioterapi", "2. Kemoterapi", "3. Rehabilitasi Medik", "4. Rehabilitasi Psikososial", "5. Transfusi Darah", "6. Pelayanan Gigi", "7. Laboratorium", "8. USG", "9. Farmasi", "10. Lain-Lain", "11. MRI", "12. HEMODIALISA" }));
        Penunjang.setEnabled(false);
        Penunjang.setName("Penunjang"); // NOI18N
        Penunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenunjangKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(Penunjang);
        Penunjang.setBounds(589, 295, 319, 23);

        FlagProsedur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Prosedur Tidak Berkelanjutan", "1. Prosedur dan Terapi Berkelanjutan" }));
        FlagProsedur.setEnabled(false);
        FlagProsedur.setName("FlagProsedur"); // NOI18N
        FlagProsedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FlagProsedurKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(FlagProsedur);
        FlagProsedur.setBounds(589, 265, 319, 23);

        jLabel56.setText("Flag Prosedur :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormKelengkapanSEP.add(jLabel56);
        jLabel56.setBounds(495, 265, 90, 23);

        jLabel57.setText("Asesmen :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormKelengkapanSEP.add(jLabel57);
        jLabel57.setBounds(495, 325, 90, 23);

        LabelPoli6.setText("DPJP Layanan :");
        LabelPoli6.setName("LabelPoli6"); // NOI18N
        FormKelengkapanSEP.add(LabelPoli6);
        LabelPoli6.setBounds(495, 355, 90, 23);

        KdDPJPLayanan.setEditable(false);
        KdDPJPLayanan.setBackground(new java.awt.Color(245, 250, 240));
        KdDPJPLayanan.setHighlighter(null);
        KdDPJPLayanan.setName("KdDPJPLayanan"); // NOI18N
        FormKelengkapanSEP.add(KdDPJPLayanan);
        KdDPJPLayanan.setBounds(589, 355, 90, 23);

        NmDPJPLayanan.setEditable(false);
        NmDPJPLayanan.setBackground(new java.awt.Color(245, 250, 240));
        NmDPJPLayanan.setHighlighter(null);
        NmDPJPLayanan.setName("NmDPJPLayanan"); // NOI18N
        FormKelengkapanSEP.add(NmDPJPLayanan);
        NmDPJPLayanan.setBounds(681, 355, 197, 23);

        btnDPJPLayanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDPJPLayanan.setMnemonic('X');
        btnDPJPLayanan.setToolTipText("Alt+X");
        btnDPJPLayanan.setName("btnDPJPLayanan"); // NOI18N
        btnDPJPLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDPJPLayananActionPerformed(evt);
            }
        });
        btnDPJPLayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDPJPLayananKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(btnDPJPLayanan);
        btnDPJPLayanan.setBounds(880, 355, 28, 23);

        jLabel34.setText("KLL :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormKelengkapanSEP.add(jLabel34);
        jLabel34.setBounds(495, 25, 90, 23);

        LakaLantas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Bukan KLL", "1. KLL Bukan KK", "2. KLL dan KK", "3. KK" }));
        LakaLantas.setName("LakaLantas"); // NOI18N
        LakaLantas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LakaLantasItemStateChanged(evt);
            }
        });
        LakaLantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakaLantasKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(LakaLantas);
        LakaLantas.setBounds(589, 25, 108, 23);

        jLabel47.setText("Tgl.Kecelakaan :");
        jLabel47.setName("jLabel47"); // NOI18N
        jLabel47.setPreferredSize(new java.awt.Dimension(55, 23));
        FormKelengkapanSEP.add(jLabel47);
        jLabel47.setBounds(724, 25, 90, 23);

        TanggalKKL.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKKL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-03-2023" }));
        TanggalKKL.setDisplayFormat("dd-MM-yyyy");
        TanggalKKL.setEnabled(false);
        TanggalKKL.setName("TanggalKKL"); // NOI18N
        TanggalKKL.setOpaque(false);
        TanggalKKL.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalKKL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKKLKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(TanggalKKL);
        TanggalKKL.setBounds(818, 25, 90, 23);

        AsesmenPoli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. Poli spesialis tidak tersedia pada hari sebelumnya", "2. Jam Poli telah berakhir pada hari sebelumnya", "3. Spesialis yang dimaksud tidak praktek pada hari sebelumnya", "4. Atas Instruksi RS", "5. Tujuan Kontrol" }));
        AsesmenPoli.setName("AsesmenPoli"); // NOI18N
        AsesmenPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsesmenPoliKeyPressed(evt);
            }
        });
        FormKelengkapanSEP.add(AsesmenPoli);
        AsesmenPoli.setBounds(589, 325, 319, 23);

        javax.swing.GroupLayout FormInputLayout = new javax.swing.GroupLayout(FormInput);
        FormInput.setLayout(FormInputLayout);
        FormInputLayout.setHorizontalGroup(
            FormInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FormInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FormKelengkapanPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FormKelengkapanSEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        FormInputLayout.setVerticalGroup(
            FormInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FormInputLayout.createSequentialGroup()
                .addComponent(FormKelengkapanPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FormKelengkapanSEP, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        scrollPane2.setViewportView(FormInput);

        PanelInput.add(scrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 760));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel16.setText("No.Kartu :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass6.add(jLabel16);

        NoKartu.setName("NoKartu"); // NOI18N
        NoKartu.setPreferredSize(new java.awt.Dimension(250, 23));
        NoKartu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKartuKeyPressed(evt);
            }
        });
        panelGlass6.add(NoKartu);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('5');
        btnPasien.setToolTipText("Alt+5");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        btnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPasienKeyPressed(evt);
            }
        });
        panelGlass6.add(btnPasien);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnCari);

        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass6.add(jLabel17);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnPrint);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnSimpan);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari1.setMnemonic('E');
        BtnCari1.setText("Cari");
        BtnCari1.setToolTipText("Alt+E");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelGlass6.add(BtnCari1);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnKeluar);

        PanelInput.add(panelGlass6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 823, 1094, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelInput, javax.swing.GroupLayout.PREFERRED_SIZE, 1054, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelInput, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NoKartuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKartuKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
            BtnPrint.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_NoKartuKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        ChkRM.setSelected(true);
        if (NoKartu.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "No.Kartu masih kosong..!!");
        } else {
            tampil(NoKartu.getText().trim());
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, NoKartu, BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            Sequel.queryu("truncate table temporary");
            int row = tabMode.getRowCount();
            for (int r = 0; r < row; r++) {
                Sequel.menyimpan("temporary", "'0','"
                        + tabMode.getValueAt(r, 0).toString() + "','"
                        + tabMode.getValueAt(r, 1).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Rekap Harian Pengadaan Ipsrs");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptCariBPJSNoPeserta.jasper", "report", "[ Pencarian Peserta BPJS Berdasarkan Nomor Kepesertaan ]", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dokter.dispose();
        dpjp.dispose();
        skdp.dispose();
        propinsikll.dispose();
        kabupatenkll.dispose();
        kecamatankll.dispose();
        akses.setAktif(false);
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed

    }//GEN-LAST:event_TNmKeyPressed

    private void CmbJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJkKeyPressed
        Valid.pindah(evt, TNm, CMbGd);
    }//GEN-LAST:event_CmbJkKeyPressed

    private void DTPLahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPLahirItemStateChanged
        lahir = DTPLahir.getDate();
        birthday = lahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        p = Period.between(birthday, today);
        p2 = ChronoUnit.DAYS.between(birthday, today);
        TUmur.setText(String.valueOf(p.getYears()) + " Th " + String.valueOf(p.getMonths()) + " Bl " + String.valueOf(p.getDays()) + " Hr");
    }//GEN-LAST:event_DTPLahirItemStateChanged

    private void DTPLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPLahirKeyPressed

    }//GEN-LAST:event_DTPLahirKeyPressed

    private void TUmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TUmurKeyPressed

    }//GEN-LAST:event_TUmurKeyPressed

    private void TNoPesertaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPesertaKeyPressed
        Valid.pindah(evt, Kdpnj, TTlp);
    }//GEN-LAST:event_TNoPesertaKeyPressed

    private void TKtpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtpKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Alamat.getText().equals("ALAMAT")) {
                Alamat.setText("");
            }
            Alamat.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Pekerjaan.requestFocus();
        }
    }//GEN-LAST:event_TKtpKeyPressed

    private void NoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRmKeyPressed

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed

    }//GEN-LAST:event_kdpoliKeyPressed

    private void TBiayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBiayaKeyPressed
        Valid.pindah(evt, kdpoli, BtnSimpan);
    }//GEN-LAST:event_TBiayaKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (empt == false) {
            emptTeks();
            BtnSimpan.setVisible(false);
        } else if (empt == true) {

            R5.setSelected(true);
            LakaLantas.setSelectedIndex(0);
            Katarak.setSelectedIndex(0);
            Suplesi.setSelectedIndex(0);
            TNo.requestFocus();
        }
    }//GEN-LAST:event_formWindowOpened

    private void MnDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDocumentActionPerformed

    }//GEN-LAST:event_MnDocumentActionPerformed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed

    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt, NoKartu, BtnPrint);
    }//GEN-LAST:event_btnPasienKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        BPJSDataSEP form = new BPJSDataSEP(null, false);
//        form.isCek();
//        form.tampil();
//        form.tutupInput();
//        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
//        form.setLocationRelativeTo(internalFrame1);
//        form.setVisible(true);
//        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        Valid.pindah(evt, BtnSimpan, BtnKeluar);
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void TTmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTmpKeyPressed
        Valid.pindah(evt, TNo, CMbGd);
    }//GEN-LAST:event_TTmpKeyPressed

    private void CMbGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbGdKeyPressed
        Valid.pindah(evt, TTmp, cmbAgama);
    }//GEN-LAST:event_CMbGdKeyPressed

    private void cmbAgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAgamaKeyPressed
        Valid.pindah(evt, CMbGd, CmbStts);
    }//GEN-LAST:event_cmbAgamaKeyPressed

    private void CmbSttsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbSttsKeyPressed
        Valid.pindah(evt, cmbAgama, Pekerjaan);
    }//GEN-LAST:event_CmbSttsKeyPressed

    private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanKeyPressed
        Valid.pindah(evt, CmbStts, CMbPnd);
    }//GEN-LAST:event_PekerjaanKeyPressed

    private void AlamatMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseMoved
        if (Alamat.getText().equals("ALAMAT")) {
            Alamat.setText("");
        }
    }//GEN-LAST:event_AlamatMouseMoved

    private void AlamatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatMouseExited
        if (Alamat.getText().equals("")) {
            Alamat.setText("ALAMAT");
        }
    }//GEN-LAST:event_AlamatMouseExited

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Alamat.getText().equals("")) {
                Alamat.setText("ALAMAT");
            }
            if (Kelurahan.getText().equals("KELURAHAN")) {
                Kelurahan.setText("");
            }
            Kelurahan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Alamat.getText().equals("")) {
                Alamat.setText("ALAMAT");
            }
            BtnCacat.requestFocus();
        }
    }//GEN-LAST:event_AlamatKeyPressed

    private void TTlpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTlpKeyPressed
        Valid.pindah(evt, CMbPnd, DTPDaftar);
    }//GEN-LAST:event_TTlpKeyPressed

    private void TNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TTmp.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            KabupatenPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            kddokter.requestFocus();
        }
    }//GEN-LAST:event_TNoKeyPressed

    private void DTPDaftarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPDaftarKeyPressed
        Valid.pindah(evt, TTlp, NmIbu);
    }//GEN-LAST:event_DTPDaftarKeyPressed

    private void CMbPndKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbPndKeyPressed
        Valid.pindah(evt, Pekerjaan, TTlp);
    }//GEN-LAST:event_CMbPndKeyPressed

    private void SaudaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaudaraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            PekerjaanPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Propinsi.getText().equals("PROPINSI")) {
                Propinsi.setText("");
            }
            Propinsi.requestFocus();
        }
    }//GEN-LAST:event_SaudaraKeyPressed

    private void KdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdpnjKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpnj, Kdpnj.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnPenjabActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnSuku.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            NmIbu.requestFocus();
        }
    }//GEN-LAST:event_KdpnjKeyPressed

    private void BtnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenjabActionPerformed
        akses.setform("DlgBridgingBPJS");
    }//GEN-LAST:event_BtnPenjabActionPerformed

    private void KelurahanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseMoved
        if (Kelurahan.getText().equals("KELURAHAN")) {
            Kelurahan.setText("");
        }
    }//GEN-LAST:event_KelurahanMouseMoved

    private void KelurahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseExited
        if (Kelurahan.getText().equals("")) {
            Kelurahan.setText("KELURAHAN");
        }
    }//GEN-LAST:event_KelurahanMouseExited

    private void KelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Kelurahan.getText().equals("")) {
                Kelurahan.setText("KELURAHAN");
            }
            if (Kecamatan.getText().equals("KECAMATAN")) {
                Kecamatan.setText("");
            }
            Kecamatan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Kelurahan.getText().equals("")) {
                Kelurahan.setText("KELURAHAN");
            }
            if (Alamat.getText().equals("ALAMAT")) {
                Alamat.setText("");
            }
            Alamat.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKelurahanActionPerformed(null);
        }
    }//GEN-LAST:event_KelurahanKeyPressed

    private void KecamatanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseMoved
        if (Kecamatan.getText().equals("KECAMATAN")) {
            Kecamatan.setText("");
        }
    }//GEN-LAST:event_KecamatanMouseMoved

    private void KecamatanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseExited
        if (Kecamatan.getText().equals("")) {
            Kecamatan.setText("KECAMATAN");
        }
    }//GEN-LAST:event_KecamatanMouseExited

    private void KecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Kecamatan.getText().equals("")) {
                Kecamatan.setText("KECAMATAN");
            }
            if (Kabupaten.getText().equals("KABUPATEN")) {
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Kecamatan.getText().equals("")) {
                Kecamatan.setText("KECAMATAN");
            }
            if (Kelurahan.getText().equals("KELURAHAN")) {
                Kelurahan.setText("");
            }
            Kelurahan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKecamatanActionPerformed(null);
        }
    }//GEN-LAST:event_KecamatanKeyPressed

    private void KabupatenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseMoved
        if (Kabupaten.getText().equals("KABUPATEN")) {
            Kabupaten.setText("");
        }
    }//GEN-LAST:event_KabupatenMouseMoved

    private void KabupatenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseExited
        if (Kabupaten.getText().equals("")) {
            Kabupaten.setText("KABUPATEN");
        }
    }//GEN-LAST:event_KabupatenMouseExited

    private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Propinsi.getText().equals("PROPINSI")) {
                Propinsi.setText("");
            }
            if (Kabupaten.getText().equals("")) {
                Kabupaten.setText("KABUPATEN");
            }
            Propinsi.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Kabupaten.getText().equals("")) {
                Kabupaten.setText("KABUPATEN");
            }
            if (Kecamatan.getText().equals("KECAMATAN")) {
                Kecamatan.setText("");
            }
            Kecamatan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKabupatenActionPerformed(null);
        }
    }//GEN-LAST:event_KabupatenKeyPressed

    private void BtnKelurahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih = 1;
    }//GEN-LAST:event_BtnKelurahanActionPerformed

    private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih = 1;
    }//GEN-LAST:event_BtnKecamatanActionPerformed

    private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih = 1;
    }//GEN-LAST:event_BtnKabupatenActionPerformed

    private void NmIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmIbuKeyPressed
        Valid.pindah(evt, DTPDaftar, Kdpnj);
    }//GEN-LAST:event_NmIbuKeyPressed

    private void PekerjaanPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanPjKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (AlamatPj.getText().equals("ALAMAT")) {
                AlamatPj.setText("");
            }
            AlamatPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Saudara.requestFocus();
        }
    }//GEN-LAST:event_PekerjaanPjKeyPressed

    private void AlamatPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatPjMouseMoved
        if (AlamatPj.getText().equals("ALAMAT")) {
            AlamatPj.setText("");
        }
    }//GEN-LAST:event_AlamatPjMouseMoved

    private void AlamatPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AlamatPjMouseExited
        if (AlamatPj.getText().equals("")) {
            AlamatPj.setText("ALAMAT");
        }
    }//GEN-LAST:event_AlamatPjMouseExited

    private void AlamatPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPjKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (AlamatPj.getText().equals("")) {
                AlamatPj.setText("ALAMAT");
            }
            if (KelurahanPj.getText().equals("KELURAHAN")) {
                KelurahanPj.setText("");
            }
            KelurahanPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (AlamatPj.getText().equals("")) {
                AlamatPj.setText("ALAMAT");
            }
            PekerjaanPj.requestFocus();
        }
    }//GEN-LAST:event_AlamatPjKeyPressed

    private void KecamatanPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanPjMouseMoved
        if (KecamatanPj.getText().equals("KECAMATAN")) {
            KecamatanPj.setText("");
        }
    }//GEN-LAST:event_KecamatanPjMouseMoved

    private void KecamatanPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanPjMouseExited
        if (KecamatanPj.getText().equals("")) {
            KecamatanPj.setText("KECAMATAN");
        }
    }//GEN-LAST:event_KecamatanPjMouseExited

    private void KecamatanPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanPjKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (KecamatanPj.getText().equals("")) {
                KecamatanPj.setText("KECAMATAN");
            }
            if (KabupatenPj.getText().equals("KABUPATEN")) {
                KabupatenPj.setText("");
            }
            KabupatenPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (KecamatanPj.getText().equals("")) {
                KecamatanPj.setText("KECAMATAN");
            }
            if (KelurahanPj.getText().equals("KELURAHAN")) {
                KelurahanPj.setText("");
            }
            KelurahanPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKecamatanPjActionPerformed(null);
        }
    }//GEN-LAST:event_KecamatanPjKeyPressed

    private void BtnKecamatanPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanPjActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih = 2;
    }//GEN-LAST:event_BtnKecamatanPjActionPerformed

    private void KabupatenPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenPjMouseMoved
        if (KabupatenPj.getText().equals("KABUPATEN")) {
            KabupatenPj.setText("");
        }
    }//GEN-LAST:event_KabupatenPjMouseMoved

    private void KabupatenPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenPjMouseExited
        if (KabupatenPj.getText().equals("")) {
            KabupatenPj.setText("KABUPATEN");
        }
    }//GEN-LAST:event_KabupatenPjMouseExited

    private void KabupatenPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenPjKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (KabupatenPj.getText().equals("")) {
                KabupatenPj.setText("KABUPATEN");
            }
            if (PropinsiPj.getText().equals("PROPINSI")) {
                PropinsiPj.setText("");
            }
            PropinsiPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (KabupatenPj.getText().equals("")) {
                KabupatenPj.setText("KABUPATEN");
            }
            if (KecamatanPj.getText().equals("KECAMATAN")) {
                KecamatanPj.setText("");
            }
            KecamatanPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKabupatenPjActionPerformed(null);
        }
    }//GEN-LAST:event_KabupatenPjKeyPressed

    private void BtnKabupatenPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenPjActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih = 2;
    }//GEN-LAST:event_BtnKabupatenPjActionPerformed

    private void BtnKelurahanPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanPjActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih = 2;
    }//GEN-LAST:event_BtnKelurahanPjActionPerformed

    private void KelurahanPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanPjMouseMoved
        if (KelurahanPj.getText().equals("KELURAHAN")) {
            KelurahanPj.setText("");
        }
    }//GEN-LAST:event_KelurahanPjMouseMoved

    private void KelurahanPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanPjMouseExited
        if (KelurahanPj.getText().equals("")) {
            KelurahanPj.setText("KELURAHAN");
        }
    }//GEN-LAST:event_KelurahanPjMouseExited

    private void KelurahanPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanPjKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (KelurahanPj.getText().equals("")) {
                KelurahanPj.setText("KELURAHAN");
            }
            if (KecamatanPj.getText().equals("KECAMATAN")) {
                KecamatanPj.setText("");
            }
            KecamatanPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (KelurahanPj.getText().equals("")) {
                KelurahanPj.setText("KELURAHAN");
            }
            if (AlamatPj.getText().equals("ALAMAT")) {
                AlamatPj.setText("");
            }
            AlamatPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKelurahanPjActionPerformed(null);
        }
    }//GEN-LAST:event_KelurahanPjKeyPressed

    private void ChkRMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRMItemStateChanged
        if (ChkRM.isSelected() == true) {
            TNo.setEditable(false);
            TNo.setBackground(new Color(245, 250, 240));
            if (statuspasien.equals("Baru")) {
                autoNomor();
            }
        } else if (ChkRM.isSelected() == false) {
            TNo.setEditable(true);
            TNo.setBackground(new Color(250, 255, 245));
        }
    }//GEN-LAST:event_ChkRMItemStateChanged

    private void BtnSukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSukuActionPerformed
        akses.setform("DlgBridgingBPJS");
    }//GEN-LAST:event_BtnSukuActionPerformed

    private void BtnSukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSukuKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSukuActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Kdpnj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnBahasa.requestFocus();
        }
    }//GEN-LAST:event_BtnSukuKeyPressed

    private void BtnBahasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBahasaActionPerformed
        akses.setform("DlgBridgingBPJS");
    }//GEN-LAST:event_BtnBahasaActionPerformed

    private void BtnBahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBahasaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBahasaActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnSuku.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCacat.requestFocus();
        }
    }//GEN-LAST:event_BtnBahasaKeyPressed

    private void BtnCacatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCacatActionPerformed
        akses.setform("DlgBridgingBPJS");
    }//GEN-LAST:event_BtnCacatActionPerformed

    private void BtnCacatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCacatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCacatActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnBahasa.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Alamat.getText().equals("ALAMAT")) {
                Alamat.setText("");
            }
            Alamat.requestFocus();
        }
    }//GEN-LAST:event_BtnCacatKeyPressed

    private void BtnPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerusahaanActionPerformed
        akses.setform("DlgBridgingBPJS");
    }//GEN-LAST:event_BtnPerusahaanActionPerformed

    private void BtnPerusahaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerusahaanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPerusahaanActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (PropinsiPj.getText().equals("PROPINSI")) {
                PropinsiPj.setText("");
            }
            PropinsiPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            EMail.requestFocus();
        }
    }//GEN-LAST:event_BtnPerusahaanKeyPressed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed
        Valid.pindah(evt, BtnPerusahaan, TNoReg);
    }//GEN-LAST:event_NIPKeyPressed

    private void EMailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EMailKeyPressed
        Valid.pindah(evt, BtnPerusahaan, NIP);
    }//GEN-LAST:event_EMailKeyPressed

    private void PropinsiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseMoved
        if (Propinsi.getText().equals("PROPINSI")) {
            Propinsi.setText("");
        }
    }//GEN-LAST:event_PropinsiMouseMoved

    private void PropinsiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseExited
        if (Propinsi.getText().equals("")) {
            Propinsi.setText("PROPINSI");
        }
    }//GEN-LAST:event_PropinsiMouseExited

    private void PropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PropinsiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Propinsi.getText().equals("")) {
                Propinsi.setText("PROPINSI");
            }
            Saudara.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Propinsi.getText().equals("")) {
                Propinsi.setText("PROPINSI");
            }
            if (Kabupaten.getText().equals("KABUPATEN")) {
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnPropinsiActionPerformed(null);
        }
    }//GEN-LAST:event_PropinsiKeyPressed

    private void BtnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPropinsiActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih = 1;
    }//GEN-LAST:event_BtnPropinsiActionPerformed

    private void PropinsiPjMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiPjMouseMoved
        if (PropinsiPj.getText().equals("PROPINSI")) {
            PropinsiPj.setText("");
        }
    }//GEN-LAST:event_PropinsiPjMouseMoved

    private void PropinsiPjMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiPjMouseExited
        if (PropinsiPj.getText().equals("")) {
            PropinsiPj.setText("PROPINSI");
        }
    }//GEN-LAST:event_PropinsiPjMouseExited

    private void PropinsiPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PropinsiPjKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (PropinsiPj.getText().equals("")) {
                PropinsiPj.setText("PROPINSI");
            }
            BtnPerusahaan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (PropinsiPj.getText().equals("")) {
                PropinsiPj.setText("PROPINSI");
            }
            if (KabupatenPj.getText().equals("KABUPATEN")) {
                KabupatenPj.setText("");
            }
            KabupatenPj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPropinsiPjActionPerformed(null);
        }
    }//GEN-LAST:event_PropinsiPjKeyPressed

    private void btnPropinsiPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropinsiPjActionPerformed
        akses.setform("DlgBridgingBPJS");
        pilih = 2;
    }//GEN-LAST:event_btnPropinsiPjActionPerformed

    private void kdperusahaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdperusahaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdperusahaanKeyPressed

    private void kdcacatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdcacatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdcacatKeyPressed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AsalRujukan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            NIP.requestFocus();
        }
    }//GEN-LAST:event_TNoRegKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (no_ktp.equals("Yes") && (TKtp.getText().trim().length() < p_no_ktp)) {
            TKtp.setText("-");
        }
        if (TNo.getText().trim().equals("")) {
            Valid.textKosong(TNo, "No.Rekam Medis");
        } else if (TNm.getText().trim().equals("")) {
            Valid.textKosong(TNm, "nama pasien");
        } else if (nmpnj.getText().trim().equals("") || Kdpnj.getText().trim().equals("")) {
            Valid.textKosong(Kdpnj, "Asuransi/Askes/Png.Jawab");
        } else if (tmp_lahir.equals("Yes") && (TTmp.getText().trim().length() < p_tmp_lahir)) {
            Valid.textKosong(TTmp, "Tempat Lahir minimal " + p_tmp_lahir + " karakter dan ");
        } else if (nm_ibu.equals("Yes") && (NmIbu.getText().trim().length() < p_nm_ibu)) {
            Valid.textKosong(NmIbu, "Nama Ibu minimal " + p_nm_ibu + " karakter dan ");
        } else if (alamat.equals("Yes") && (Alamat.getText().trim().length() < p_alamat)) {
            Valid.textKosong(Alamat, "Alamat Pasien minimal " + p_alamat + " karakter dan ");
        } else if (pekerjaan.equals("Yes") && (Pekerjaan.getText().trim().length() < p_pekerjaan)) {
            Valid.textKosong(Pekerjaan, "Pekerjaan Pasien minimal " + p_pekerjaan + " karakter dan ");
        } else if (no_tlp.equals("Yes") && (TTlp.getText().trim().length() < p_no_tlp)) {
            Valid.textKosong(TTlp, "Telp Pasien minimal " + p_no_tlp + " karakter dan ");
        } else if (umur.equals("Yes") && (TUmur.getText().trim().length() < p_umur)) {
            Valid.textKosong(TUmur, "Umur Pasien minimal " + p_umur + " karakter dan ");
        } else if (namakeluarga.equals("Yes") && (Saudara.getText().trim().length() < p_namakeluarga)) {
            Valid.textKosong(Saudara, "Penanggung Jawab Pasien minimal " + p_namakeluarga + " karakter dan ");
        } else if (no_peserta.equals("Yes") && (TNoPeserta.getText().trim().length() < p_no_peserta)) {
            Valid.textKosong(TNoPeserta, "No.Peserta Pasien minimal " + p_no_peserta + " karakter dan ");
        } else if (kelurahan.equals("Yes") && (Kelurahan.getText().trim().length() < p_kelurahan)) {
            Valid.textKosong(Kelurahan, "Kelurahan minimal " + p_kelurahan + " karakter dan ");
        } else if (kecamatan.equals("Yes") && (Kecamatan.getText().trim().length() < p_kecamatan)) {
            Valid.textKosong(Kecamatan, "Kecamatan minimal " + p_kecamatan + " karakter dan ");
        } else if (kabupaten.equals("Yes") && (Kabupaten.getText().trim().length() < p_kabupaten)) {
            Valid.textKosong(Kabupaten, "Kabupaten minimal " + p_kabupaten + " karakter dan ");
        } else if (pekerjaanpj.equals("Yes") && (PekerjaanPj.getText().trim().length() < p_pekerjaanpj)) {
            Valid.textKosong(PekerjaanPj, "Pekerjaan P.J. minimal " + p_pekerjaanpj + " karakter dan ");
        } else if (alamatpj.equals("Yes") && (AlamatPj.getText().trim().length() < p_alamatpj)) {
            Valid.textKosong(AlamatPj, "Alamat P.J. minimal " + p_alamatpj + " karakter dan ");
        } else if (kelurahanpj.equals("Yes") && (KelurahanPj.getText().trim().length() < p_kelurahanpj)) {
            Valid.textKosong(KelurahanPj, "Kelurahan P.J. minimal " + p_kelurahanpj + " karakter dan ");
        } else if (kecamatanpj.equals("Yes") && (KecamatanPj.getText().trim().length() < p_kecamatanpj)) {
            Valid.textKosong(KecamatanPj, "Kecamatan P.J. minimal " + p_kecamatanpj + " karakter dan ");
        } else if (kabupatenpj.equals("Yes") && (KabupatenPj.getText().trim().length() < p_kabupatenpj)) {
            Valid.textKosong(KabupatenPj, "Kabupaten P.J. minimal " + p_kabupatenpj + " karakter dan ");
        } else if (propinsi.equals("Yes") && (Propinsi.getText().trim().length() < p_propinsi)) {
            Valid.textKosong(Propinsi, "Propinsi minimal " + p_propinsi + " karakter dan ");
        } else if (propinsipj.equals("Yes") && (PropinsiPj.getText().trim().length() < p_propinsipj)) {
            Valid.textKosong(PropinsiPj, "Propinsi P.J. minimal " + p_propinsipj + " karakter dan ");
        } else if (nmsukubangsa.getText().trim().equals("")) {
            Valid.textKosong(nmsukubangsa, "Suku/Bangsa");
        } else if (nmbahasa.getText().trim().equals("")) {
            Valid.textKosong(nmbahasa, "Bahasa");
        } else if (nmperusahaan.getText().trim().equals("")) {
            Valid.textKosong(nmperusahaan, "Perusahaan/Instansi");
        } else if (nmcacat.getText().trim().equals("")) {
            Valid.textKosong(nmcacat, "Cacat Fisik");
        } else if (TNoReg.getText().trim().equals("")) {
            Valid.textKosong(TNoReg, "No.Regristrasi");
        } else if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else if (TDokter.getText().trim().equals("")) {
            Valid.textKosong(kddokter, "dokter");
        } else if ((LakaLantas.getSelectedIndex() == 1) && Keterangan.getText().equals("")) {
            Valid.textKosong(Keterangan, "Keterangan");
        } else if ((LakaLantas.getSelectedIndex() == 1) && NmPropinsi.getText().equals("")) {
            Valid.textKosong(btnPropinsi, "Propinsi");
        } else if ((LakaLantas.getSelectedIndex() == 1) && NmKabupaten.getText().equals("")) {
            Valid.textKosong(btnKabupaten, "Kabupaten");
        } else if ((LakaLantas.getSelectedIndex() == 1) && NmKecamatan.getText().equals("")) {
            Valid.textKosong(btnKecamatan, "Kecamatan");
        } else if (KdDPJP.getText().trim().equals("") || NmDPJP.getText().trim().equals("")) {
            Valid.textKosong(KdDPJP, "DPJP");
        }/*else if (NoSKDP.getText().trim().equals("")) {
            Valid.textKosong(NoSKDP, "No.SKDP");
        }*/ else if (Sequel.cariInteger("select count(pasien.no_rkm_medis) from pasien inner join reg_periksa inner join kamar_inap "
                + "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat "
                + "where kamar_inap.stts_pulang='-' and pasien.no_rkm_medis=?", TNo.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "Pasien sedang dalam masa perawatan di kamar inap..!!");
            NoRujukan.requestFocus();
        } else if (NoRujukan.getText().trim().equals("")) {
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        } else if (KdPpkRujukan.getText().trim().equals("") || NmPpkRujukan.getText().trim().equals("")) {
            Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
        } else if (KdPPK.getText().trim().equals("") || NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        } else if (KdPenyakit.getText().trim().equals("") || NmPenyakit.getText().trim().equals("")) {
            Valid.textKosong(KdPenyakit, "Diagnosa");
        } else if (KdPoli.getText().trim().equals("") || NmPoli.getText().trim().equals("")) {
            Valid.textKosong(KdPoli, "Poli/Kamar");
        } else if (Catatan.getText().trim().equals("")) {
            Valid.textKosong(Catatan, "Catatan");
        } else {
            if ((JenisPelayanan.getSelectedIndex() == 1) && (NmPoli.getText().toLowerCase().contains("darurat"))) {
                if (Sequel.cariInteger("select count(no_kartu) from bridging_sep where "
                        + "no_kartu='" + no_peserta + "' and jnspelayanan='" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "' "
                        + "and tglsep like '%" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "").substring(0, 10) + "%' and "
                        + "nmpolitujuan like '%darurat%'") >= 3) {
                    JOptionPane.showMessageDialog(null, "Maaf, sebelumnya sudah dilakukan 3x pembuatan SEP di jenis pelayanan yang sama..!!");
                    NoRujukan.requestFocus();
                } else {
                    insertPasien();
                }
            } else if ((JenisPelayanan.getSelectedIndex() == 1) && (!NmPoli.getText().toLowerCase().contains("darurat"))) {
                if (Sequel.cariInteger("select count(no_kartu) from bridging_sep where "
                        + "no_kartu='" + no_peserta + "' and jnspelayanan='" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "' "
                        + "and tglsep like '%" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "").substring(0, 10) + "%' and "
                        + "nmpolitujuan not like '%darurat%'") >= 1) {
                    JOptionPane.showMessageDialog(null, "Maaf, sebelumnya sudah dilakukan pembuatan SEP di jenis pelayanan rawat jalan..!!");
                    NoRujukan.requestFocus();
                } else {
                    insertPasien();
                }
            } else if (JenisPelayanan.getSelectedIndex() == 0) {
                insertPasien();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnDokter, NoRujukan);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void AsalRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukanKeyPressed

    }//GEN-LAST:event_AsalRujukanKeyPressed

    private void TanggalSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSEPKeyPressed
        Valid.pindah(evt, LakaLantas, TanggalKKL);
    }//GEN-LAST:event_TanggalSEPKeyPressed

    private void TanggalRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujukKeyPressed
        Valid.pindah(evt, COB, Catatan);
    }//GEN-LAST:event_TanggalRujukKeyPressed

    private void NoRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRujukanKeyPressed
        Valid.pindah(evt, Catatan, LakaLantas);
    }//GEN-LAST:event_NoRujukanKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt, TanggalRujuk, NoRujukan);
    }//GEN-LAST:event_CatatanKeyPressed

    private void JenisPelayananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisPelayananItemStateChanged
        if (JenisPelayanan.getSelectedIndex() == 1) {
            LabelPoli.setText("Poli Tujuan :");
            NaikKelas.setEnabled(false);
            NaikKelas.setSelectedIndex(0);
            Pembiayaan.setEnabled(false);
            Pembiayaan.setSelectedIndex(0);
            PenanggungJawab.setEditable(false);
            PenanggungJawab.setText("");
            btnDPJPLayanan.setEnabled(true);
            //btnPoli.setVisible(false);
        } else if (JenisPelayanan.getSelectedIndex() == 0) {
            LabelPoli.setText("Kamar Tujuan :");
            NaikKelas.setEnabled(true);
            Pembiayaan.setEnabled(true);
            PenanggungJawab.setEditable(true);
            KdDPJPLayanan.setText("");
            NmDPJPLayanan.setText("");
            btnDPJPLayanan.setEnabled(false);
            //btnPoli.setVisible(true);
        }
        KdPoli.setText("");
        NmPoli.setText("");
    }//GEN-LAST:event_JenisPelayananItemStateChanged

    private void JenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayananKeyPressed

    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt, JenisPelayanan, Katarak);
    }//GEN-LAST:event_KelasKeyPressed

    private void EksekutifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EksekutifKeyPressed
        Valid.pindah(evt, Katarak, COB);
    }//GEN-LAST:event_EksekutifKeyPressed

    private void COBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_COBKeyPressed
        Valid.pindah(evt, Eksekutif, TanggalRujuk);
    }//GEN-LAST:event_COBKeyPressed

    private void KatarakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KatarakKeyPressed
        Valid.pindah(evt, Kelas, Eksekutif);
    }//GEN-LAST:event_KatarakKeyPressed

    private void NaikKelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NaikKelasItemStateChanged
        if (NaikKelas.getSelectedIndex() > 0) {
            if (Pembiayaan.getSelectedIndex() == 0) {
                Pembiayaan.setSelectedIndex(1);
            }
        } else if (NaikKelas.getSelectedIndex() == 0) {
            Pembiayaan.setSelectedIndex(0);
        }
    }//GEN-LAST:event_NaikKelasItemStateChanged

    private void NaikKelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NaikKelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NaikKelasKeyPressed

    private void PembiayaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PembiayaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PembiayaanKeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isNumber();
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", TDokter, kddokter.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnDokterActionPerformed(null);
        } else {
            Valid.pindah(evt, COB, BtnSimpan);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(PanelInput.getWidth() - 20, PanelInput.getHeight() - 20);
        dokter.setLocationRelativeTo(PanelInput);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        Valid.pindah(evt, btnKecamatan, BtnSimpan);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt, TanggalKKL, Suplesi);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void SuplesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuplesiKeyPressed
        Valid.pindah(evt, Keterangan, NoSEPSuplesi);
    }//GEN-LAST:event_SuplesiKeyPressed

    private void NoSEPSuplesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSEPSuplesiKeyPressed
        Valid.pindah(evt, Suplesi, btnPropinsi);
    }//GEN-LAST:event_NoSEPSuplesiKeyPressed

    private void btnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropinsiActionPerformed
        propinsikll.setSize(PanelInput.getWidth() - 20, PanelInput.getHeight() - 20);
        propinsikll.setLocationRelativeTo(PanelInput);
        propinsikll.setVisible(true);
    }//GEN-LAST:event_btnPropinsiActionPerformed

    private void btnPropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPropinsiKeyPressed
        Valid.pindah(evt, NoSEPSuplesi, btnKabupaten);
    }//GEN-LAST:event_btnPropinsiKeyPressed

    private void btnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKabupatenActionPerformed
        if (KdPropinsi.getText().trim().equals("") || NmPropinsi.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih propinsi dulu..!!");
            btnPropinsi.requestFocus();
        } else {
            kabupatenkll.setPropinsi(KdPropinsi.getText(), NmPropinsi.getText());
            kabupatenkll.setSize(PanelInput.getWidth() - 20, PanelInput.getHeight() - 20);
            kabupatenkll.setLocationRelativeTo(PanelInput);
            kabupatenkll.setVisible(true);
        }
    }//GEN-LAST:event_btnKabupatenActionPerformed

    private void btnKabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKabupatenKeyPressed
        Valid.pindah(evt, btnPropinsi, btnKecamatan);
    }//GEN-LAST:event_btnKabupatenKeyPressed

    private void btnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKecamatanActionPerformed
        if (KdKabupaten.getText().trim().equals("") || NmKabupaten.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih kabupaten dulu..!!");
            btnKabupaten.requestFocus();
        } else {
            kecamatankll.setPropinsi(KdKabupaten.getText(), NmKabupaten.getText());
            kecamatankll.setSize(PanelInput.getWidth() - 20, PanelInput.getHeight() - 20);
            kecamatankll.setLocationRelativeTo(PanelInput);
            kecamatankll.setVisible(true);
        }
    }//GEN-LAST:event_btnKecamatanActionPerformed

    private void btnKecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKecamatanKeyPressed
        Valid.pindah(evt, btnKabupaten, BtnDokter);
    }//GEN-LAST:event_btnKecamatanKeyPressed

    private void TujuanKunjunganItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TujuanKunjunganItemStateChanged
        if (TujuanKunjungan.getSelectedIndex() == 0) {
            FlagProsedur.setEnabled(false);
            FlagProsedur.setSelectedIndex(0);
            Penunjang.setEnabled(false);
            Penunjang.setSelectedIndex(0);
            AsesmenPoli.setEnabled(true);
        } else {
            if (TujuanKunjungan.getSelectedIndex() == 1) {
                AsesmenPoli.setSelectedIndex(0);
                AsesmenPoli.setEnabled(false);
            } else {
                AsesmenPoli.setEnabled(true);
            }
            if (FlagProsedur.getSelectedIndex() == 0) {
                FlagProsedur.setSelectedIndex(2);
            }
            FlagProsedur.setEnabled(true);
            if (Penunjang.getSelectedIndex() == 0) {
                Penunjang.setSelectedIndex(10);
            }
            Penunjang.setEnabled(true);
        }
    }//GEN-LAST:event_TujuanKunjunganItemStateChanged

    private void TujuanKunjunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TujuanKunjunganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TujuanKunjunganKeyPressed

    private void PenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenunjangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenunjangKeyPressed

    private void FlagProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FlagProsedurKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_FlagProsedurKeyPressed

    private void btnDPJPLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPLayananActionPerformed
        pilih = 2;
        dpjp.setPoli(KdPoli.getText(), NmPoli.getText());
        dpjp.setSize(PanelInput.getWidth() - 20, PanelInput.getHeight() - 20);
        dpjp.setLocationRelativeTo(PanelInput);
        dpjp.setVisible(true);
    }//GEN-LAST:event_btnDPJPLayananActionPerformed

    private void btnDPJPLayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJPLayananKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDPJPLayananKeyPressed

    private void ppPengajuanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuanBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if (tbKamar.getRowCount() > 0) {
//            try {
//                URL = link + "/Sep/pengajuanSEP";
//                headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//                headers.add("X-Cons-ID", koneksiDB.CONSIDAPIBPJS());
//                utc = String.valueOf(api.GetUTCdatetimeAsString());
//                headers.add("X-Timestamp", utc);
//                headers.add("X-Signature", api.getHmac(utc));
//                headers.add("user_key", koneksiDB.USERKEYAPIBPJS());
//                requestJson = " {"
//                        + "\"request\": {"
//                        + "\"t_sep\": {"
//                        + "\"noKartu\": \"" + TNoPeserta.getText() + "\","
//                        + "\"tglSep\": \"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
//                        + "\"jnsPelayanan\": \"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
//                        + "\"jnsPengajuan\": \"1\","
//                        + "\"keterangan\": \"" + Catatan.getText() + "\","
//                        + "\"user\": \"" + user + "\""
//                        + "}"
//                        + "}"
//                        + "}";
//                requestEntity = new HttpEntity(requestJson, headers);
//                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
//                nameNode = root.path("metaData");
//                System.out.println("code : " + nameNode.path("code").asText());
//                System.out.println("message : " + nameNode.path("message").asText());
//                if (nameNode.path("code").asText().equals("200")) {
//                    JOptionPane.showMessageDialog(null, "Proses mapping selesai, data nomor rawat berhasil dikirim ke SEP..!!");
//                } else {
//                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
//                }
//            } catch (Exception ex) {
//                System.out.println("Notifikasi Bridging : " + ex);
//                if (ex.toString().contains("UnknownHostException")) {
//                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Maaf, silahkan masukkan data yang mau dimapping transaksinya...!!!!");
//            NoRujukan.requestFocus();
//        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuanBtnPrintActionPerformed

    private void ppPengajuan1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan1BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if (tbKamar.getRowCount() > 0) {
//            try {
//                URL = link + "/Sep/aprovalSEP";
//                headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//                headers.add("X-Cons-ID", koneksiDB.CONSIDAPIBPJS());
//                utc = String.valueOf(api.GetUTCdatetimeAsString());
//                headers.add("X-Timestamp", utc);
//                headers.add("X-Signature", api.getHmac(utc));
//                headers.add("user_key", koneksiDB.USERKEYAPIBPJS());
//                requestJson = " {"
//                        + "\"request\": {"
//                        + "\"t_sep\": {"
//                        + "\"noKartu\": \"" + TNoPeserta.getText() + "\","
//                        + "\"tglSep\": \"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
//                        + "\"jnsPelayanan\": \"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
//                        + "\"jnsPengajuan\": \"1\","
//                        + "\"keterangan\": \"" + Catatan.getText() + "\","
//                        + "\"user\": \"" + user + "\""
//                        + "}"
//                        + "}"
//                        + "}";
//                requestEntity = new HttpEntity(requestJson, headers);
//                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
//                nameNode = root.path("metaData");
//                System.out.println("code : " + nameNode.path("code").asText());
//                System.out.println("message : " + nameNode.path("message").asText());
//                if (nameNode.path("code").asText().equals("200")) {
//                    JOptionPane.showMessageDialog(null, "Proses mapping selesai, data nomor rawat berhasil dikirim ke SEP..!!");
//                } else {
//                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
//                }
//            } catch (Exception ex) {
//                System.out.println("Notifikasi Bridging : " + ex);
//                if (ex.toString().contains("UnknownHostException")) {
//                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Maaf, silahkan masukkan data yang mau dimapping transaksinya...!!!!");
//            NoRujukan.requestFocus();
//        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuan1BtnPrintActionPerformed

    private void ppPengajuan2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan2BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if (tbKamar.getRowCount() > 0) {
//            try {
//                URL = link + "/Sep/pengajuanSEP";
//                headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//                headers.add("X-Cons-ID", koneksiDB.CONSIDAPIBPJS());
//                utc = String.valueOf(api.GetUTCdatetimeAsString());
//                headers.add("X-Timestamp", utc);
//                headers.add("X-Signature", api.getHmac(utc));
//                headers.add("user_key", koneksiDB.USERKEYAPIBPJS());
//                requestJson = " {"
//                        + "\"request\": {"
//                        + "\"t_sep\": {"
//                        + "\"noKartu\": \"" + TNoPeserta.getText() + "\","
//                        + "\"tglSep\": \"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
//                        + "\"jnsPelayanan\": \"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
//                        + "\"jnsPengajuan\": \"2\","
//                        + "\"keterangan\": \"" + Catatan.getText() + "\","
//                        + "\"user\": \"" + user + "\""
//                        + "}"
//                        + "}"
//                        + "}";
//                requestEntity = new HttpEntity(requestJson, headers);
//                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
//                nameNode = root.path("metaData");
//                System.out.println("code : " + nameNode.path("code").asText());
//                System.out.println("message : " + nameNode.path("message").asText());
//                if (nameNode.path("code").asText().equals("200")) {
//                    JOptionPane.showMessageDialog(null, "Proses mapping selesai, data nomor rawat berhasil dikirim ke SEP..!!");
//                } else {
//                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
//                }
//            } catch (Exception ex) {
//                System.out.println("Notifikasi Bridging : " + ex);
//                if (ex.toString().contains("UnknownHostException")) {
//                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Maaf, silahkan masukkan data yang mau dimapping transaksinya...!!!!");
//            NoRujukan.requestFocus();
//        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuan2BtnPrintActionPerformed

    private void ppPengajuan3BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan3BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if (tbKamar.getRowCount() > 0) {
//            try {
//                URL = link + "/Sep/aprovalSEP";
//                headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//                headers.add("X-Cons-ID", koneksiDB.CONSIDAPIBPJS());
//                utc = String.valueOf(api.GetUTCdatetimeAsString());
//                headers.add("X-Timestamp", utc);
//                headers.add("X-Signature", api.getHmac(utc));
//                headers.add("user_key", koneksiDB.USERKEYAPIBPJS());
//                requestJson = " {"
//                        + "\"request\": {"
//                        + "\"t_sep\": {"
//                        + "\"noKartu\": \"" + TNoPeserta.getText() + "\","
//                        + "\"tglSep\": \"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
//                        + "\"jnsPelayanan\": \"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
//                        + "\"jnsPengajuan\": \"2\","
//                        + "\"keterangan\": \"" + Catatan.getText() + "\","
//                        + "\"user\": \"" + user + "\""
//                        + "}"
//                        + "}"
//                        + "}";
//                requestEntity = new HttpEntity(requestJson, headers);
//                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
//                nameNode = root.path("metaData");
//                System.out.println("code : " + nameNode.path("code").asText());
//                System.out.println("message : " + nameNode.path("message").asText());
//                if (nameNode.path("code").asText().equals("200")) {
//                    JOptionPane.showMessageDialog(null, "Proses mapping selesai, data nomor rawat berhasil dikirim ke SEP..!!");
//                } else {
//                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
//                }
//            } catch (Exception ex) {
//                System.out.println("Notifikasi Bridging : " + ex);
//                if (ex.toString().contains("UnknownHostException")) {
//                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Maaf, silahkan masukkan data yang mau dimapping transaksinya...!!!!");
//            NoRujukan.requestFocus();
//        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuan3BtnPrintActionPerformed

    private void ppStatusFingerBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStatusFingerBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID", koneksiDB.CONSIDAPIBPJS());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp", utc);
            headers.add("X-Signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIBPJS());
            URL = link + "/SEP/FingerPrint/Peserta/" + TNoPeserta.getText() + "/TglPelayanan/" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "");
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : " + nameNode.path("code").asText());
            //System.out.println("message : "+nameNode.path("message").asText());
            if (nameNode.path("code").asText().equals("200")) {
                response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc));
                JOptionPane.showMessageDialog(null, response.path("status").asText());
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi Bridging : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            }
        }

        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppStatusFingerBtnPrintActionPerformed

    private void LakaLantasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LakaLantasItemStateChanged
        if (LakaLantas.getSelectedIndex() == 0) {
            TanggalKKL.setEnabled(false);
            Keterangan.setEditable(false);
            Keterangan.setText("");
            Suplesi.setEnabled(false);
            Suplesi.setSelectedIndex(0);
            NoSEPSuplesi.setText("");
            NoSEPSuplesi.setEditable(false);
            KdPropinsi.setText("");
            NmPropinsi.setText("");
            btnPropinsi.setEnabled(false);
            KdKabupaten.setText("");
            NmKabupaten.setText("");
            btnKabupaten.setEnabled(false);
            KdKecamatan.setText("");
            NmKecamatan.setText("");
            btnKecamatan.setEnabled(false);
        } else {
            TanggalKKL.setEnabled(true);
            Keterangan.setEditable(true);
            Suplesi.setEnabled(true);
            NoSEPSuplesi.setEditable(true);
            btnPropinsi.setEnabled(true);
            btnKabupaten.setEnabled(true);
            btnKecamatan.setEnabled(true);
        }
    }//GEN-LAST:event_LakaLantasItemStateChanged

    private void LakaLantasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LakaLantasKeyPressed
        Valid.pindah(evt, NoRujukan, TanggalSEP);
    }//GEN-LAST:event_LakaLantasKeyPressed

    private void TanggalKKLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKKLKeyPressed
        Valid.pindah(evt, TanggalSEP, Keterangan);
    }//GEN-LAST:event_TanggalKKLKeyPressed

    private void AsesmenPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsesmenPoliKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AsesmenPoliKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSCekRujukanKartuPCare dialog = new BPJSCekRujukanKartuPCare(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox Alamat;
    private widget.TextBox AlamatPj;
    private widget.ComboBox AsalRujukan;
    private widget.ComboBox AsesmenPoli;
    private widget.Button BtnBahasa;
    private widget.Button BtnCacat;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnDokter;
    private widget.Button BtnKabupaten;
    private widget.Button BtnKabupatenPj;
    private widget.Button BtnKecamatan;
    private widget.Button BtnKecamatanPj;
    private widget.Button BtnKeluar;
    private widget.Button BtnKelurahan;
    private widget.Button BtnKelurahanPj;
    private widget.Button BtnPenjab;
    private widget.Button BtnPerusahaan;
    private widget.Button BtnPrint;
    private widget.Button BtnPropinsi;
    private widget.Button BtnSimpan;
    private widget.Button BtnSuku;
    private widget.ComboBox CMbGd;
    private widget.ComboBox CMbPnd;
    private widget.ComboBox COB;
    private widget.TextBox Catatan;
    private widget.CekBox ChkRM;
    private widget.ComboBox CmbJk;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPDaftar;
    private widget.Tanggal DTPLahir;
    private javax.swing.JDialog DlgSEP;
    private widget.TextBox EMail;
    private widget.ComboBox Eksekutif;
    private widget.ComboBox FlagProsedur;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormKelengkapanPasien;
    private widget.PanelBiasa FormKelengkapanSEP;
    private widget.ComboBox JenisPelayanan;
    private widget.TextBox Kabupaten;
    private widget.TextBox KabupatenPj;
    private widget.ComboBox Katarak;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdDPJPLayanan;
    private widget.TextBox KdKabupaten;
    private widget.TextBox KdKecamatan;
    private widget.TextBox KdPPK;
    private widget.TextBox KdPenyakit;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPpkRujukan;
    private widget.TextBox KdPropinsi;
    private widget.TextBox Kdpnj;
    private widget.TextBox Kecamatan;
    private widget.TextBox KecamatanPj;
    private widget.ComboBox Kelas;
    private widget.TextBox Kelurahan;
    private widget.TextBox KelurahanPj;
    private widget.TextBox Keterangan;
    private widget.Label LabelKelas;
    private widget.Label LabelKelas1;
    private widget.Label LabelKelas2;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli2;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli5;
    private widget.Label LabelPoli6;
    private widget.ComboBox LakaLantas;
    private javax.swing.JMenuItem MnDocument;
    private widget.TextBox NIP;
    private widget.ComboBox NaikKelas;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmDPJPLayanan;
    private widget.TextBox NmIbu;
    private widget.TextBox NmKabupaten;
    private widget.TextBox NmKecamatan;
    private widget.TextBox NmPPK;
    private widget.TextBox NmPenyakit;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPpkRujukan;
    private widget.TextBox NmPropinsi;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoKartu;
    private widget.TextBox NoRm;
    private widget.TextBox NoRujukan;
    private widget.TextBox NoSEPSuplesi;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pekerjaan;
    private widget.TextBox PekerjaanPj;
    private widget.ComboBox Pembiayaan;
    private widget.TextBox PenanggungJawab;
    private widget.ComboBox Penunjang;
    private widget.TextBox Propinsi;
    private widget.TextBox PropinsiPj;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton R4;
    private widget.RadioButton R5;
    private widget.RadioButton R6;
    private widget.TextBox Saudara;
    private widget.ComboBox Suplesi;
    private widget.TextBox TBiaya;
    private widget.TextBox TDokter;
    private widget.TextBox TKtp;
    private widget.TextBox TNm;
    private widget.TextBox TNo;
    private widget.TextBox TNoPeserta;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TPoli;
    private widget.TextBox TTlp;
    private widget.TextBox TTmp;
    private widget.TextBox TUmur;
    private widget.Tanggal TanggalKKL;
    private widget.Tanggal TanggalRujuk;
    private widget.Tanggal TanggalSEP;
    private widget.ComboBox TujuanKunjungan;
    private widget.Button btnDPJPLayanan;
    private widget.Button btnKabupaten;
    private widget.Button btnKecamatan;
    private widget.Button btnPasien;
    private widget.Button btnPropinsi;
    private widget.Button btnPropinsiPj;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.ComboBox cmbAgama;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel52;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbahasa;
    private widget.TextBox kdcacat;
    private widget.TextBox kddokter;
    private widget.TextBox kdgolonganpolri;
    private widget.TextBox kdgolongantni;
    private widget.TextBox kdjabatanpolri;
    private widget.TextBox kdjabatantni;
    private widget.TextBox kdpangkatpolri;
    private widget.TextBox kdpangkattni;
    private widget.TextBox kdperusahaan;
    private widget.TextBox kdpoli;
    private widget.TextBox kdsatuanpolri;
    private widget.TextBox kdsatuantni;
    private widget.TextBox kdsuku;
    private widget.TextBox nmbahasa;
    private widget.TextBox nmcacat;
    private widget.TextBox nmperusahaan;
    private widget.TextBox nmpnj;
    private widget.TextBox nmsukubangsa;
    private widget.panelisi panelGlass6;
    private javax.swing.JMenuItem ppPengajuan;
    private javax.swing.JMenuItem ppPengajuan1;
    private javax.swing.JMenuItem ppPengajuan2;
    private javax.swing.JMenuItem ppPengajuan3;
    private javax.swing.JMenuItem ppStatusFinger;
    private widget.ScrollPane scrollPane2;
    // End of variables declaration//GEN-END:variables

    public void tampil(String nomorrujukan) {
        try {
            URL = link + "/Rujukan/Peserta/" + nomorrujukan;
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.CONSIDAPIBPJS());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp", utc);
            headers.add("X-Signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIBPJS());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("URL : " + URL);
            peserta = "";
            if (nameNode.path("code").asText().equals("200")) {
                Valid.tabelKosong(tabMode);
                response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc)).path("rujukan");
                System.out.println(response);
                KdPenyakit.setText(response.path("diagnosa").path("kode").asText());
                NmPenyakit.setText(response.path("diagnosa").path("nama").asText());
                tabMode.addRow(new Object[]{
                    "Keluhan", ": " + response.path("keluhan").asText()
                });
                NoRujukan.setText(response.path("noKunjungan").asText());
                tabMode.addRow(new Object[]{
                    "Pelayanan", ": " + response.path("pelayanan").path("kode").asText() + " " + response.path("pelayanan").path("nama").asText()
                });
                tabMode.addRow(new Object[]{
                    "Peserta", ": "
                });
                tabMode.addRow(new Object[]{
                    "       COB", ": "
                });
                tabMode.addRow(new Object[]{
                    "              Nama Asuransi", ": " + response.path("peserta").path("cob").path("nmAsuransi").asText()
                });
                tabMode.addRow(new Object[]{
                    "              No. Asuransi", ": " + response.path("peserta").path("cob").path("noAsuransi").asText()
                });
                tabMode.addRow(new Object[]{
                    "              Tanggal TAT", ": " + response.path("peserta").path("cob").path("tglTAT").asText()
                });
                tabMode.addRow(new Object[]{
                    "              Tanggal TMT", ": " + response.path("peserta").path("cob").path("tglTMT").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Hak Kelas", ": " + response.path("peserta").path("hakKelas").path("kode").asText() + ". " + response.path("peserta").path("hakKelas").path("keterangan").asText()
                });
                Kelas.setSelectedItem(response.path("peserta").path("hakKelas").path("kode").asText() + ". " + response.path("peserta").path("hakKelas").path("keterangan").asText().replaceAll("KELAS", "Kelas"));
                tabMode.addRow(new Object[]{
                    "       Informasi", ": "
                });
                tabMode.addRow(new Object[]{
                    "              Dinsos", ": " + response.path("peserta").path("informasi").path("dinsos").asText()
                });
                tabMode.addRow(new Object[]{
                    "              No.SKTM", ": " + response.path("peserta").path("informasi").path("noSKTM").asText()
                });
                tabMode.addRow(new Object[]{
                    "              Prolanis PRB", ": " + response.path("peserta").path("informasi").path("prolanisPRB").asText()
                });
                prb = response.path("peserta").path("informasi").path("prolanisPRB").asText().replaceAll("null", "");
                tabMode.addRow(new Object[]{
                    "       Jenis Peserta", ": " + response.path("peserta").path("jenisPeserta").path("kode").asText() + ". " + response.path("peserta").path("jenisPeserta").path("keterangan").asText()
                });
                Pekerjaan.setText(response.path("peserta").path("jenisPeserta").path("keterangan").asText());
                peserta = response.path("peserta").path("jenisPeserta").path("keterangan").asText();
                tabMode.addRow(new Object[]{
                    "       Medical Record", ": "
                });
                tabMode.addRow(new Object[]{
                    "              Nomor RM", ": " + response.path("peserta").path("mr").path("noMR").asText()
                });
                tabMode.addRow(new Object[]{
                    "              Nomor Telp", ": " + response.path("peserta").path("mr").path("noTelepon").asText()
                });
                TTlp.setText(response.path("peserta").path("mr").path("noTelepon").asText());
                tabMode.addRow(new Object[]{
                    "       Nama Pasien", ": " + response.path("peserta").path("nama").asText()
                });
                TNm.setText(response.path("peserta").path("nama").asText());
                tabMode.addRow(new Object[]{
                    "       NIK", ": " + response.path("peserta").path("nik").asText()
                });
                TKtp.setText(response.path("peserta").path("nik").asText());
                tabMode.addRow(new Object[]{
                    "       No.Kartu", ": " + response.path("peserta").path("noKartu").asText()
                });
                TNoPeserta.setText(response.path("peserta").path("noKartu").asText());
                tabMode.addRow(new Object[]{
                    "       Pisa", ": " + response.path("peserta").path("pisa").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Provider", ": " + response.path("peserta").path("provUmum").path("kdProvider").asText() + " " + response.path("peserta").path("provUmum").path("nmProvider").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Jenis Kelamin", ": " + response.path("peserta").path("sex").asText().replaceAll("L", "Laki-Laki").replaceAll("P", "Perempuan")
                });
                CmbJk.setSelectedItem(response.path("peserta").path("sex").asText());
                tabMode.addRow(new Object[]{
                    "       Status Peserta", ": " + response.path("peserta").path("statusPeserta").path("kode").asText() + " " + response.path("peserta").path("statusPeserta").path("keterangan").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Tgl. Cetak Kartu", ": " + response.path("peserta").path("tglCetakKartu").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Tgl. Lahir", ": " + response.path("peserta").path("tglLahir").asText()
                });
                Valid.SetTgl(DTPLahir, response.path("peserta").path("tglLahir").asText());
                tabMode.addRow(new Object[]{
                    "       Tgl. TAT", ": " + response.path("peserta").path("tglTAT").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Tgl. TMT", ": " + response.path("peserta").path("tglTMT").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Umur Saat Pelayanan", ": " + response.path("peserta").path("umur").path("umurSaatPelayanan").asText()
                });
                tabMode.addRow(new Object[]{
                    "       Umur Sekarang", ": " + response.path("peserta").path("umur").path("umurSekarang").asText()
                });
                TUmur.setText(response.path("peserta").path("umur").path("umurSekarang").asText().replaceAll("tahun", "Th ").replaceAll("bulan", "Bl ").replaceAll("hari", "Hr"));
                tabMode.addRow(new Object[]{
                    "Poli Rujukan", ": " + response.path("poliRujukan").path("kode").asText() + " " + response.path("poliRujukan").path("nama").asText()
                });
                KdPoli.setText(response.path("poliRujukan").path("kode").asText());
                kdpoli.setText(Sequel.cariIsi("select kd_poli_rs from maping_poli_bpjs where kd_poli_bpjs=?", response.path("poliRujukan").path("kode").asText()));
                if (!kdpoli.getText().equals("")) {
                    isPoli();
                } else {
                    if (!response.path("poliRujukan").path("kode").asText().equals("")) {
                        int jawab = JOptionPane.showConfirmDialog(null, "Mapping poli tidak ditemukan.\nSilahkan lakukan mapping terlebih dahulu..!!", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                        if (jawab == JOptionPane.YES_OPTION) {
                            poli.isCek();
                            poli.setSize(PanelInput.getWidth() - 20, PanelInput.getHeight() - 20);
                            poli.setLocationRelativeTo(PanelInput);
                            poli.setVisible(true);
                        }
                    }
                }
                NmPoli.setText(response.path("poliRujukan").path("nama").asText());
                tabMode.addRow(new Object[]{
                    "Provider Perujuk", ": " + response.path("provPerujuk").path("kode").asText() + " " + response.path("provPerujuk").path("nama").asText()
                });
                KdPpkRujukan.setText(response.path("provPerujuk").path("kode").asText());
                NmPpkRujukan.setText(response.path("provPerujuk").path("nama").asText());
                tabMode.addRow(new Object[]{
                    "Tanggal Kunjungan", ": " + response.path("tglKunjungan").asText()
                });
                Valid.SetTgl(TanggalRujuk, response.path("tglKunjungan").asText());
                isNumber();
                Kdpnj.setText("BPJ");
                nmpnj.setText("BPJS");
                ps = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                        + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, pasien.alamat,kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop,"
                        + "pasien.gol_darah, pasien.pekerjaan,pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                        + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.no_peserta,pasien.pekerjaanpj,"
                        + "pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,pasien.kabupatenpj,pasien.propinsipj,"
                        + "perusahaan_pasien.kode_perusahaan,perusahaan_pasien.nama_perusahaan,pasien.bahasa_pasien,"
                        + "bahasa_pasien.nama_bahasa,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,pasien.nip,pasien.email,cacat_fisik.nama_cacat,pasien.cacat_fisik from pasien "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten inner join perusahaan_pasien inner join cacat_fisik inner join propinsi "
                        + "inner join bahasa_pasien inner join suku_bangsa inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.cacat_fisik=cacat_fisik.id "
                        + "and pasien.kd_kel=kelurahan.kd_kel and perusahaan_pasien.kode_perusahaan=pasien.perusahaan_pasien and pasien.kd_prop=propinsi.kd_prop "
                        + "and bahasa_pasien.id=pasien.bahasa_pasien and suku_bangsa.id=pasien.suku_bangsa and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                        + "where pasien.no_peserta=?");
                try {
                    ps.setString(1, response.path("peserta").path("noKartu").asText());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        statuspasien = "Lama";
                        TNo.setText(rs.getString("no_rkm_medis"));
                        TNm.setText(rs.getString("nm_pasien"));
                        CMbGd.setSelectedItem(rs.getString("gol_darah"));
                        TTmp.setText(rs.getString("tmp_lahir"));
                        cmbAgama.setSelectedItem(rs.getString("agama"));
                        CmbStts.setSelectedItem(rs.getString("stts_nikah"));
                        Alamat.setText(rs.getString("alamat"));
                        AlamatPj.setText(rs.getString("alamatpj"));
                        Pekerjaan.setText(rs.getString("pekerjaan"));
                        PekerjaanPj.setText(rs.getString("pekerjaanpj"));
                        TTlp.setText(rs.getString("no_tlp"));
                        Saudara.setText(rs.getString("namakeluarga"));
                        NmIbu.setText(rs.getString("nm_ibu"));
                        Kelurahan.setText(rs.getString("nm_kel"));
                        Kecamatan.setText(rs.getString("nm_kec"));
                        Kabupaten.setText(rs.getString("nm_kab"));
                        Propinsi.setText(rs.getString("nm_prop"));
                        KelurahanPj.setText(rs.getString("kelurahanpj"));
                        KecamatanPj.setText(rs.getString("kecamatanpj"));
                        KabupatenPj.setText(rs.getString("kabupatenpj"));
                        PropinsiPj.setText(rs.getString("propinsipj"));
                        EMail.setText(rs.getString("email"));
                        NIP.setText(rs.getString("nip"));
                        kdsuku.setText(rs.getString("suku_bangsa"));
                        nmsukubangsa.setText(rs.getString("nama_suku_bangsa"));
                        kdbahasa.setText(rs.getString("bahasa_pasien"));
                        nmbahasa.setText(rs.getString("nama_bahasa"));
                        kdcacat.setText(rs.getString("cacat_fisik"));
                        nmcacat.setText(rs.getString("nama_cacat"));
                        kdperusahaan.setText(rs.getString("kode_perusahaan"));
                        nmperusahaan.setText(rs.getString("nama_perusahaan"));
                        switch (rs.getString("namakeluarga")) {
                            case "AYAH":
                                R1.setSelected(true);
                                break;
                            case "IBU":
                                R2.setSelected(true);
                                break;
                            case "ISTRI":
                                R3.setSelected(true);
                                break;
                            case "SUAMI":
                                R4.setSelected(true);
                                break;
                            case "SAUDARA":
                                R5.setSelected(true);
                                break;
                            case "ANAK":
                                R6.setSelected(true);
                                break;
                        }
                    } else {
                        statuspasien = "Baru";
                        autoNomor();
                    }
                } catch (Exception e) {
                    System.out.println("Notif Cari Pasien : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
                Catatan.setText(statuspasien);
                ps = koneksi.prepareStatement(
                        "select maping_dokter_dpjpvclaim.kd_dokter,maping_dokter_dpjpvclaim.kd_dokter_bpjs,maping_dokter_dpjpvclaim.nm_dokter_bpjs from maping_dokter_dpjpvclaim inner join jadwal "
                        + "on maping_dokter_dpjpvclaim.kd_dokter=jadwal.kd_dokter where jadwal.kd_poli=? and jadwal.hari_kerja=?");
                try {
                    if (day == 1) {
                        hari = "AKHAD";
                    } else if (day == 2) {
                        hari = "SENIN";
                    } else if (day == 3) {
                        hari = "SELASA";
                    } else if (day == 4) {
                        hari = "RABU";
                    } else if (day == 5) {
                        hari = "KAMIS";
                    } else if (day == 6) {
                        hari = "JUMAT";
                    } else if (day == 7) {
                        hari = "SABTU";
                    }

                    ps.setString(1, kdpoli.getText());
                    ps.setString(2, hari);
                    rs = ps.executeQuery();

                    if (rs.next()) {

                        KdDPJP.setText(rs.getString("kd_dokter_bpjs"));
                        NmDPJP.setText(rs.getString("nm_dokter_bpjs"));
                        kddokter.setText(rs.getString("kd_dokter"));
                        TDokter.setText(rs.getString("nm_dokter_bpjs"));
                        KdDPJPLayanan.setText(rs.getString("kd_dokter_bpjs"));
                        NmDPJPLayanan.setText(rs.getString("nm_dokter_bpjs"));
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }

//                PanelInput.setVisible(true);
                FormInput.setPreferredSize(new Dimension(955, 730));
                if (PanelInput.getHeight() > 530) {
                    PanelInput.setPreferredSize(new Dimension(WIDTH, 570));
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    if (PanelInput.getWidth() < 960) {
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952, 335));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952, 394));
                    } else {
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(PanelInput.getWidth() - 32, 335));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(PanelInput.getWidth() - 32, 394));
                    }
                } else {
                    scrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    PanelInput.setPreferredSize(new Dimension(WIDTH, PanelInput.getHeight() - 20));
                    if (PanelInput.getWidth() < 960) {
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(952, 335));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(952, 394));
                    } else {
                        scrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        FormKelengkapanPasien.setPreferredSize(new Dimension(PanelInput.getWidth() - 32, 335));
                        FormKelengkapanSEP.setPreferredSize(new Dimension(PanelInput.getWidth() - 32, 394));
                    }
                }
                isNumber();
                BtnSimpan.setVisible(true);
                TTmp.requestFocus();
            } else {
                emptTeks();
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    public void emptTeks() {
        TNo.setText("");
        TNm.setText("");
        CmbJk.setSelectedIndex(0);
        CMbGd.setSelectedIndex(0);
        TTmp.setText("");
        cmbAgama.setSelectedIndex(0);
        CmbStts.setSelectedIndex(0);
        Alamat.setText("ALAMAT");
        AlamatPj.setText("ALAMAT");
        TKtp.setText("");
        TNoPeserta.setText("");
        Pekerjaan.setText("");
        PekerjaanPj.setText("");
        TTlp.setText("");
        TUmur.setText("");
        Saudara.setText("");
        NmIbu.setText("");
        Kelurahan.setText("KELURAHAN");
        Kecamatan.setText("KECAMATAN");
        Kabupaten.setText("KABUPATEN");
        Propinsi.setText("PROPINSI");
        KelurahanPj.setText("KELURAHAN");
        KecamatanPj.setText("KECAMATAN");
        KabupatenPj.setText("KABUPATEN");
        PropinsiPj.setText("PROPINSI");
        kdcacat.setText("");
        nmcacat.setText("");
        NIP.setText("");
        EMail.setText("");
        kdsuku.setText("");
        nmsukubangsa.setText("");
        kdbahasa.setText("");
        nmbahasa.setText("");
        kdperusahaan.setText("");
        nmperusahaan.setText("");
        kdgolongantni.setText("");

        kdsatuantni.setText("");

        kdpangkattni.setText("");

        kdjabatantni.setText("");
        kdgolonganpolri.setText("");

        kdsatuanpolri.setText("");

        kdpangkatpolri.setText("");

        kdjabatanpolri.setText("");
        R5.setSelected(true);
        DTPLahir.setDate(new Date());
        DTPDaftar.setDate(new Date());
        TNoReg.setText("");
        TNoRw.setText("");
        Kdpnj.setText("");
        nmpnj.setText("");
        TanggalSEP.setDate(new Date());
        TanggalRujuk.setDate(new Date());
        NoKartu.setText("");
        KdPpkRujukan.setText("");
        NmPpkRujukan.setText("");
        Catatan.setText("");
        KdPenyakit.setText("");
        NmPenyakit.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        Kelas.setSelectedIndex(2);
        LakaLantas.setSelectedIndex(0);
        Katarak.setSelectedIndex(0);
        Suplesi.setSelectedIndex(0);
        NoSEPSuplesi.setText("");
        Keterangan.setText("");
        KdPropinsi.setText("");
        NmPropinsi.setText("");
        KdKabupaten.setText("");
        NmKabupaten.setText("");
        KdKecamatan.setText("");
        NmKecamatan.setText("");
        KdDPJP.setText("");
        NmDPJP.setText("");
        TanggalKKL.setDate(new Date());
        JenisPelayanan.setSelectedIndex(1);
        JenisPelayananItemStateChanged(null);
        statuspasien = "";
        TBiaya.setText("0");
        TanggalKKL.setEnabled(false);
        Keterangan.setEditable(false);
        Keterangan.setText("");
        Suplesi.setEnabled(false);
        NoSEPSuplesi.setText("");
        NoSEPSuplesi.setEditable(false);
        btnPropinsi.setEnabled(false);
        btnKabupaten.setEnabled(false);
        btnKecamatan.setEnabled(false);
        NaikKelas.setEnabled(false);
        NaikKelas.setSelectedIndex(0);
        Pembiayaan.setEnabled(false);
        Pembiayaan.setSelectedIndex(0);
        PenanggungJawab.setEditable(false);
        PenanggungJawab.setText("");
        TujuanKunjungan.setSelectedIndex(0);
        FlagProsedur.setSelectedIndex(0);
        FlagProsedur.setEnabled(false);
        Penunjang.setSelectedIndex(0);
        Penunjang.setEnabled(false);
        AsesmenPoli.setSelectedIndex(0);
        AsesmenPoli.setEnabled(true);
        KdDPJPLayanan.setText("");
        NmDPJPLayanan.setText("");
        btnDPJPLayanan.setEnabled(true);
        TNo.requestFocus();
    }

    private void isNumber() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "' ", "BR/" + dateformat.format(TanggalSEP.getDate()) + "/", 4, NoBalasan);
        if (BASENOREG.equals("booking")) {
            switch (URUTNOREG) {
                case "poli":
                    if (Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'")
                            >= Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'")) {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    } else {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    }
                    break;
                case "dokter":
                    if (Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + kddokter.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'")
                            >= Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'")) {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + kddokter.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    } else {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    }
                    break;
                case "dokter + poli":
                    if (Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'")
                            >= Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'")) {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    } else {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    }
                    break;
                default:
                    if (Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'")
                            >= Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'")) {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    } else {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    }
                    break;
            }
        } else {
            switch (URUTNOREG) {
                case "poli":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    break;
                case "dokter":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    break;
                case "dokter + poli":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    break;
                default:
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    break;
            }
        }
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "").substring(0, 10) + "' ", dateformat.format(TanggalSEP.getDate()) + "/", 6, TNoRw);
    }

    private void isPoli() {
        try {
            ps = koneksi.prepareStatement("select registrasi, registrasilama "
                    + " from poliklinik where kd_poli=? order by nm_poli");
            try {
                ps.setString(1, kdpoli.getText().trim());
                rs = ps.executeQuery();
                if (rs.next()) {
                    if (statuspasien.equals("Lama")) {
                        TBiaya.setText(rs.getString("registrasilama"));
                    } else if (statuspasien.equals("Baru")) {
                        TBiaya.setText(rs.getString("registrasi"));
                    } else {
                        TBiaya.setText(rs.getString("registrasi"));
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif Cari Poli : " + e);
        }
    }

    private void autoNomor() {
        if (ChkRM.isSelected() == true) {
            if (tahun.equals("Yes")) {
                awalantahun = DTPDaftar.getSelectedItem().toString().substring(8, 10);
            } else {
                awalantahun = "";
            }

            if (bulan.equals("Yes")) {
                awalanbulan = DTPDaftar.getSelectedItem().toString().substring(3, 5);
            } else {
                awalanbulan = "";
            }

            if (posisitahun.equals("Depan")) {
                switch (pengurutan) {
                    case "Straight":
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rkm_medis,6),signed)),0) from set_no_rkm_medis", "", 6, NoRm);
                        break;
                    case "Terminal":
                        Valid.autoNomer4("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(no_rkm_medis,6),5,2),SUBSTRING(RIGHT(no_rkm_medis,6),3,2),SUBSTRING(RIGHT(no_rkm_medis,6),1,2)),signed)),0) from set_no_rkm_medis", "", 6, NoRm);
                        break;
                    case "Middle":
                        Valid.autoNomer5("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(no_rkm_medis,6),3,2),SUBSTRING(RIGHT(no_rkm_medis,6),1,2),SUBSTRING(RIGHT(no_rkm_medis,6),5,2)),signed)),0) from set_no_rkm_medis", "", 6, NoRm);
                        break;
                }
            } else if (posisitahun.equals("Belakang")) {
                switch (pengurutan) {
                    case "Straight":
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(LEFT(no_rkm_medis,6),signed)),0) from set_no_rkm_medis", "", 6, NoRm);
                        break;
                    case "Terminal":
                        Valid.autoNomer4("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(no_rkm_medis,6),5,2),SUBSTRING(LEFT(no_rkm_medis,6),3,2),SUBSTRING(LEFT(no_rkm_medis,6),1,2)),signed)),0) from set_no_rkm_medis", "", 6, NoRm);
                        break;
                    case "Middle":
                        Valid.autoNomer5("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(no_rkm_medis,6),3,2),SUBSTRING(LEFT(no_rkm_medis,6),1,2),SUBSTRING(LEFT(no_rkm_medis,6),5,2)),signed)),0) from set_no_rkm_medis", "", 6, NoRm);
                        break;
                }
            }

            if (posisitahun.equals("Depan")) {
                TNo.setText(awalantahun + awalanbulan + NoRm.getText());
            } else if (posisitahun.equals("Belakang")) {
                if (!(awalanbulan + awalantahun).equals("")) {
                    TNo.setText(NoRm.getText() + "-" + awalanbulan + awalantahun);
                } else {
                    TNo.setText(NoRm.getText());
                }
            }
        }
    }

    private void insertPasien() {
        if (R1.isSelected() == true) {
            klg = "AYAH";
        } else if (R2.isSelected() == true) {
            klg = "IBU";
        } else if (R3.isSelected() == true) {
            klg = "ISTRI";
        } else if (R4.isSelected() == true) {
            klg = "SUAMI";
        } else if (R5.isSelected() == true) {
            klg = "SAUDARA";
        } else if (R6.isSelected() == true) {
            klg = "ANAK";
        }

        if (Kelurahan.isEditable() == true) {
            Sequel.queryu4("insert into kelurahan values(?,?)", 2, new String[]{"0", Kelurahan.getText().replaceAll("KELURAHAN", "-")});
            kdkel = Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?", Kelurahan.getText().replaceAll("KELURAHAN", "-"));
        } else if (Kelurahan.isEditable() == false) {
            if (kdkel.equals("")) {
                Sequel.queryu4("insert into kelurahan values(?,?)", 2, new String[]{"0", Kelurahan.getText().replaceAll("KELURAHAN", "-")});
                kdkel = Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?", Kelurahan.getText().replaceAll("KELURAHAN", "-"));
            }
        }

        if (Kecamatan.isEditable() == true) {
            Sequel.queryu4("insert into kecamatan values(?,?)", 2, new String[]{"0", Kecamatan.getText().replaceAll("KECAMATAN", "-")});
            kdkec = Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?", Kecamatan.getText().replaceAll("KECAMATAN", "-"));
        } else if (Kecamatan.isEditable() == false) {
            if (kdkec.equals("")) {
                Sequel.queryu4("insert into kecamatan values(?,?)", 2, new String[]{"0", Kecamatan.getText().replaceAll("KECAMATAN", "-")});
                kdkec = Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?", Kecamatan.getText().replaceAll("KECAMATAN", "-"));
            }
        }

        if (Kabupaten.isEditable() == true) {
            Sequel.queryu4("insert into kabupaten values(?,?)", 2, new String[]{"0", Kabupaten.getText().replaceAll("KABUPATEN", "-")});
            kdkab = Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?", Kabupaten.getText().replaceAll("KABUPATEN", "-"));
        } else if (Kabupaten.isEditable() == false) {
            if (kdkab.equals("")) {
                Sequel.queryu4("insert into kabupaten values(?,?)", 2, new String[]{"0", Kabupaten.getText().replaceAll("KABUPATEN", "-")});
                kdkab = Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?", Kabupaten.getText().replaceAll("KABUPATEN", "-"));
            }
        }

        if (Propinsi.isEditable() == true) {
            Sequel.queryu4("insert into propinsi values(?,?)", 2, new String[]{"0", Propinsi.getText().replaceAll("PROPINSI", "-")});
            kdprop = Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?", Propinsi.getText().replaceAll("PROPINSI", "-"));
        } else if (Propinsi.isEditable() == false) {
            if (kdprop.equals("")) {
                Sequel.queryu4("insert into propinsi values(?,?)", 2, new String[]{"0", Propinsi.getText().replaceAll("PROPINSI", "-")});
                kdprop = Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?", Propinsi.getText().replaceAll("PROPINSI", "-"));
            }
        }

        if (statuspasien.equals("Baru")) {
            autoNomor();
            if (Sequel.menyimpantf2("pasien", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rekam Medis Pasien", 36, new String[]{
                TNo.getText(), TNm.getText(), TKtp.getText(), CmbJk.getSelectedItem().toString().substring(0, 1), TTmp.getText(),
                Valid.SetTgl(DTPLahir.getSelectedItem() + ""), NmIbu.getText(),
                Alamat.getText().replaceAll("ALAMAT", ""), CMbGd.getSelectedItem().toString(), Pekerjaan.getText(), CmbStts.getSelectedItem().toString(), cmbAgama.getSelectedItem().toString(),
                DTPDaftar.getSelectedItem().toString().substring(6, 10) + "-" + DTPDaftar.getSelectedItem().toString().substring(3, 5) + "-" + DTPDaftar.getSelectedItem().toString().substring(0, 2),
                TTlp.getText(), TUmur.getText(), CMbPnd.getSelectedItem().toString(), klg, Saudara.getText(), Kdpnj.getText(), TNoPeserta.getText(),
                kdkel, kdkec, kdkab, PekerjaanPj.getText(), AlamatPj.getText(), KelurahanPj.getText(), KecamatanPj.getText(), KabupatenPj.getText(), kdperusahaan.getText(),
                kdsuku.getText(), kdbahasa.getText(), kdcacat.getText(), EMail.getText(), NIP.getText(),
                kdprop, PropinsiPj.getText()
            }) == true) {
                if (ChkRM.isSelected() == true) {
                    Sequel.queryu2("delete from set_no_rkm_medis");
                    Sequel.queryu2("insert into set_no_rkm_medis values(?)", 1, new String[]{TNo.getText()});
                }
                inputRegistrasi();
            } else {
                autoNomor();
                if (Sequel.menyimpantf2("pasien", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rekam Medis Pasien", 36, new String[]{
                    TNo.getText(), TNm.getText(), TKtp.getText(), CmbJk.getSelectedItem().toString().substring(0, 1), TTmp.getText(),
                    Valid.SetTgl(DTPLahir.getSelectedItem() + ""), NmIbu.getText(),
                    Alamat.getText().replaceAll("ALAMAT", ""), CMbGd.getSelectedItem().toString(), Pekerjaan.getText(), CmbStts.getSelectedItem().toString(), cmbAgama.getSelectedItem().toString(),
                    DTPDaftar.getSelectedItem().toString().substring(6, 10) + "-" + DTPDaftar.getSelectedItem().toString().substring(3, 5) + "-" + DTPDaftar.getSelectedItem().toString().substring(0, 2),
                    TTlp.getText(), TUmur.getText(), CMbPnd.getSelectedItem().toString(), klg, Saudara.getText(), Kdpnj.getText(), TNoPeserta.getText(),
                    kdkel, kdkec, kdkab, PekerjaanPj.getText(), AlamatPj.getText(), KelurahanPj.getText(), KecamatanPj.getText(), KabupatenPj.getText(), kdperusahaan.getText(),
                    kdsuku.getText(), kdbahasa.getText(), kdcacat.getText(), EMail.getText(), NIP.getText(),
                    kdprop, PropinsiPj.getText()
                }) == true) {
                    if (ChkRM.isSelected() == true) {
                        Sequel.queryu2("delete from set_no_rkm_medis");
                        Sequel.queryu2("insert into set_no_rkm_medis values(?)", 1, new String[]{TNo.getText()});
                    }
                    inputRegistrasi();
                } else {
                    autoNomor();
                }
            }
        } else if (statuspasien.equals("Lama")) {
            if (kdkel.equals("")) {
                kdkel = Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?", Kelurahan.getText().replaceAll("KELURAHAN", "-"));
            }
            if (kdkec.equals("")) {
                kdkec = Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?", Kecamatan.getText().replaceAll("KECAMATAN", "-"));
            }
            if (kdkab.equals("")) {
                kdkab = Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?", Kabupaten.getText().replaceAll("KABUPATEN", "-"));
            }
            if (kdprop.equals("")) {
                kdprop = Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?", Propinsi.getText().replaceAll("PROPINSI", "-"));
            }
            Sequel.mengedit("pasien", "no_rkm_medis=?", "no_rkm_medis=?,nm_pasien=?,no_ktp=?,jk=?,tmp_lahir=?,"
                    + "tgl_lahir=?,alamat=?,gol_darah=?,pekerjaan=?,stts_nikah=?,agama=?,tgl_daftar=?,no_tlp=?,umur=?"
                    + ",pnd=?,keluarga=?,namakeluarga=?,kd_pj=?,no_peserta=?,kd_kel=?,kd_kec=?,kd_kab=?,nm_ibu=?,pekerjaanpj=?,"
                    + "alamatpj=?,kelurahanpj=?,kecamatanpj=?,kabupatenpj=?,perusahaan_pasien=?,suku_bangsa=?,bahasa_pasien=?,"
                    + "cacat_fisik=?,email=?,nip=?,kd_prop=?,propinsipj=?", 37,
                    new String[]{TNo.getText(), TNm.getText(), TKtp.getText(), CmbJk.getSelectedItem().toString().substring(0, 1), TTmp.getText(),
                        Valid.SetTgl(DTPLahir.getSelectedItem() + ""),
                        Alamat.getText(), CMbGd.getSelectedItem().toString(), Pekerjaan.getText(), CmbStts.getSelectedItem().toString(), cmbAgama.getSelectedItem().toString(),
                        DTPDaftar.getSelectedItem().toString().substring(6, 10) + "-" + DTPDaftar.getSelectedItem().toString().substring(3, 5) + "-" + DTPDaftar.getSelectedItem().toString().substring(0, 2),
                        TTlp.getText(), TUmur.getText(), CMbPnd.getSelectedItem().toString(), klg, Saudara.getText(), Kdpnj.getText(), TNoPeserta.getText(),
                        kdkel, kdkec, kdkab, NmIbu.getText(), PekerjaanPj.getText(), AlamatPj.getText(), KelurahanPj.getText(), KecamatanPj.getText(),
                        KabupatenPj.getText(), kdperusahaan.getText(), kdsuku.getText(), kdbahasa.getText(), kdcacat.getText(), EMail.getText(), NIP.getText(),
                        kdprop, PropinsiPj.getText(), TNo.getText()
                    });
            inputRegistrasi();
        }
    }

    private void inputRegistrasi() {
        umurdaftar = "0";
        sttsumur = "Th";
        try {
            pscariumur = koneksi.prepareStatement(
                    "select TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) as tahun, "
                    + "(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "
                    + "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari "
                    + "from pasien where no_rkm_medis=?");
            try {
                pscariumur.setString(1, TNo.getText());
                rs = pscariumur.executeQuery();
                if (rs.next()) {
                    if (rs.getInt("tahun") > 0) {
                        umurdaftar = rs.getString("tahun");
                        sttsumur = "Th";
                    } else if (rs.getInt("tahun") == 0) {
                        if (rs.getInt("bulan") > 0) {
                            umurdaftar = rs.getString("bulan");
                            sttsumur = "Bl";
                        } else if (rs.getInt("bulan") == 0) {
                            umurdaftar = rs.getString("hari");
                            sttsumur = "Hr";
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Umur : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pscariumur != null) {
                    pscariumur.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

        status = "Baru";
        if (Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis='" + TNo.getText() + "' and kd_poli='" + kdpoli.getText() + "'") > 0) {
            status = "Lama";
        }
        if (JenisPelayanan.getSelectedIndex() == 1) {
            isPoli();
            isNumber();
            if (Sequel.menyimpantf2("reg_periksa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 19,
                    new String[]{TNoReg.getText(), TNoRw.getText(), Valid.SetTgl(TanggalSEP.getSelectedItem() + ""), TanggalSEP.getSelectedItem().toString().substring(11, 19),
                        kddokter.getText(), TNo.getText(), kdpoli.getText(), Saudara.getText(), AlamatPj.getText() + ", " + KelurahanPj.getText() + ", " + KecamatanPj.getText() + ", " + KabupatenPj.getText(),
                        klg, TBiaya.getText(), "Belum", statuspasien, "Ralan", Kdpnj.getText(), umurdaftar, sttsumur, "Belum Bayar", status
                    }) == true) {
                UpdateUmur();
                if (nosisrute.equals("")) {
                    Sequel.menyimpan2("rujuk_masuk", "'" + TNoRw.getText() + "','" + NmPpkRujukan.getText() + "','" + Kabupaten.getText() + "','" + NoRujukan.getText() + "','0','" + NmPPK.getText() + "','" + KdPenyakit.getText() + "','-','-','" + NoBalasan.getText() + "'", "No.Rujuk");
                } else {
                    Sequel.menyimpan2("rujuk_masuk", "'" + TNoRw.getText() + "','" + NmPpkRujukan.getText() + "','" + Kabupaten.getText() + "','" + nosisrute + "','0','" + NmPPK.getText() + "','" + KdPenyakit.getText() + "','-','-','" + NoBalasan.getText() + "'", "No.Rujuk");
                }
                Sequel.menyimpan2("penyakit", "?,?,?,?,?,?", "Penyakit", 6, new String[]{KdPenyakit.getText(), NmPenyakit.getText(), NmPenyakit.getText(), "-", "-", "Tidak Menular"});
                if (Sequel.cariInteger(
                        "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "
                        + "inner join reg_periksa inner join pasien on "
                        + "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "
                        + "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "
                        + "pasien.no_rkm_medis='" + TNo.getText() + "' and diagnosa_pasien.kd_penyakit='" + KdPenyakit.getText() + "'") > 0) {
                    Sequel.menyimpan2("diagnosa_pasien", "?,?,?,?,?", "Penyakit", 5, new String[]{TNoRw.getText(), KdPenyakit.getText(), "Ralan", "1", "Lama"});
                } else {
                    Sequel.menyimpan2("diagnosa_pasien", "?,?,?,?,?", "Penyakit", 5, new String[]{TNoRw.getText(), KdPenyakit.getText(), "Ralan", "1", "Baru"});
                }
                insertSEP();
            }
        }
    }

    private void insertSEP() {
        try {
            tglkkl = "0000-00-00";
            if (LakaLantas.getSelectedIndex() == 1) {
                tglkkl = Valid.SetTgl(TanggalKKL.getSelectedItem() + "");
            }

            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID", koneksiDB.CONSIDAPIBPJS());
            utc = String.valueOf(api.GetUTCdatetimeAsString());
            headers.add("X-Timestamp", utc);
            headers.add("X-Signature", api.getHmac(utc));
            headers.add("user_key", koneksiDB.USERKEYAPIBPJS());
            URL = link + "/SEP/2.0/insert";
            requestJson = "{"
                    + "\"request\":{"
                    + "\"t_sep\":{"
                    + "\"noKartu\":\"" + NoKartu.getText() + "\","
                    + "\"tglSep\":\"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
                    + "\"ppkPelayanan\":\"" + KdPPK.getText() + "\","
                    + "\"jnsPelayanan\":\"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"klsRawat\":{"
                    + "\"klsRawatHak\":\"" + Kelas.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"klsRawatNaik\":\"" + (NaikKelas.getSelectedIndex() > 0 ? NaikKelas.getSelectedItem().toString().substring(0, 1) : "") + "\","
                    + "\"pembiayaan\":\"" + (Pembiayaan.getSelectedIndex() > 0 ? Pembiayaan.getSelectedItem().toString().substring(0, 1) : "") + "\","
                    + "\"penanggungJawab\":\"" + (PenanggungJawab.getText().equals("") ? "" : PenanggungJawab.getText()) + "\""
                    + "},"
                    + "\"noMR\":\"" + TNo.getText() + "\","
                    + "\"rujukan\": {"
                    + "\"asalRujukan\":\"" + AsalRujukan.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"tglRujukan\":\"" + Valid.SetTgl(TanggalRujuk.getSelectedItem() + "") + "\","
                    + "\"noRujukan\":\"" + NoRujukan.getText() + "\","
                    + "\"ppkRujukan\":\"" + KdPpkRujukan.getText() + "\""
                    + "},"
                    + "\"catatan\":\"" + Catatan.getText() + "\","
                    + "\"diagAwal\":\"" + KdPenyakit.getText() + "\","
                    + "\"poli\": {"
                    + "\"tujuan\": \"" + KdPoli.getText() + "\","
                    + "\"eksekutif\": \"" + Eksekutif.getSelectedItem().toString().substring(0, 1) + "\""
                    + "},"
                    + "\"cob\": {"
                    + "\"cob\": \"" + COB.getSelectedItem().toString().substring(0, 1) + "\""
                    + "},"
                    + "\"katarak\": {"
                    + "\"katarak\": \"" + Katarak.getSelectedItem().toString().substring(0, 1) + "\""
                    + "},"
                    + "\"jaminan\": {"
                    + "\"lakaLantas\":\"" + LakaLantas.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"penjamin\": {"
                    + "\"tglKejadian\": \"" + tglkkl.replaceAll("0000-00-00", "") + "\","
                    + "\"keterangan\": \"" + Keterangan.getText() + "\","
                    + "\"suplesi\": {"
                    + "\"suplesi\": \"" + Suplesi.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"noSepSuplesi\": \"" + NoSEPSuplesi.getText() + "\","
                    + "\"lokasiLaka\": {"
                    + "\"kdPropinsi\": \"" + KdPropinsi.getText() + "\","
                    + "\"kdKabupaten\": \"" + KdKabupaten.getText() + "\","
                    + "\"kdKecamatan\": \"" + KdKecamatan.getText() + "\""
                    + "}"
                    + "}"
                    + "}"
                    + "},"
                    + "\"tujuanKunj\": \"" + TujuanKunjungan.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"flagProcedure\": \"" + (FlagProsedur.getSelectedIndex() > 0 ? FlagProsedur.getSelectedItem().toString().substring(0, 1) : "") + "\","
                    + "\"kdPenunjang\": \"" + (Penunjang.getSelectedIndex() > 0 ? Penunjang.getSelectedIndex() + "" : "") + "\","
                    + "\"assesmentPel\": \"" + (AsesmenPoli.getSelectedIndex() > 0 ? AsesmenPoli.getSelectedItem().toString().substring(0, 1) : "") + "\","
                    + "\"skdp\": {"
                    + "\"noSurat\": \"\","
                    + "\"kodeDPJP\": \"" + KdDPJP.getText() + "\""
                    + "},"
                    + "\"dpjpLayan\": \"" + (KdDPJPLayanan.getText().equals("") ? "" : KdDPJPLayanan.getText()) + "\","
                    + "\"noTelp\": \"" + TTlp.getText() + "\","
                    + "\"user\":\"APMINDRIATI\""
                    + "}"
                    + "}"
                    + "}";
            System.out.println("JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            requestJson = api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody();
            System.out.println("JSON : " + requestJson);
            root = mapper.readTree(requestJson);
            nameNode = root.path("metaData");
            System.out.println("code : " + nameNode.path("code").asText());
            System.out.println("message : " + nameNode.path("message").asText());
            JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            if (nameNode.path("code").asText().equals("200")) {
                response = mapper.readTree(api.Decrypt(root.path("response").asText(), utc)).path("sep").path("noSep");
                nosep = response.asText();
                System.out.println("No.SEP : " + nosep);
                if (Sequel.menyimpantf2("bridging_sep", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "SEP", 52, new String[]{
                    response.asText(), TNoRw.getText(), Valid.SetTgl(TanggalSEP.getSelectedItem() + ""), Valid.SetTgl(TanggalRujuk.getSelectedItem() + ""), NoRujukan.getText(), KdPpkRujukan.getText(),
                    NmPpkRujukan.getText(), KdPPK.getText(), NmPPK.getText(), JenisPelayanan.getSelectedItem().toString().substring(0, 1), Catatan.getText(), KdPenyakit.getText(), NmPenyakit.getText(),
                    KdPoli.getText(), NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0, 1), (NaikKelas.getSelectedIndex() > 0 ? NaikKelas.getSelectedItem().toString().substring(0, 1) : ""),
                    (Pembiayaan.getSelectedIndex() > 0 ? Pembiayaan.getSelectedItem().toString().substring(0, 1) : ""), (PenanggungJawab.getText().equals("") ? "" : PenanggungJawab.getText()),
                    LakaLantas.getSelectedItem().toString().substring(0, 1), "APMINDRIATI", TNo.getText(), TNm.getText(), Valid.SetTgl(DTPLahir.getSelectedItem() + ""), peserta, CmbJk.getSelectedItem().toString(), NoKartu.getText(),
                    "0000-00-00 00:00:00", AsalRujukan.getSelectedItem().toString(), Eksekutif.getSelectedItem().toString(), COB.getSelectedItem().toString(), TTlp.getText(), Katarak.getSelectedItem().toString(),
                    tglkkl, Keterangan.getText(), Suplesi.getSelectedItem().toString(), NoSEPSuplesi.getText(), KdPropinsi.getText(), NmPropinsi.getText(), KdKabupaten.getText(), NmKabupaten.getText(),
                    KdKecamatan.getText(), NmKecamatan.getText(), "", KdDPJP.getText(), NmDPJP.getText(), TujuanKunjungan.getSelectedItem().toString().substring(0, 1),
                    (FlagProsedur.getSelectedIndex() > 0 ? FlagProsedur.getSelectedItem().toString().substring(0, 1) : ""), (Penunjang.getSelectedIndex() > 0 ? Penunjang.getSelectedIndex() + "" : ""),
                    (AsesmenPoli.getSelectedIndex() > 0 ? AsesmenPoli.getSelectedItem().toString().substring(0, 1) : ""), KdDPJPLayanan.getText(), NmDPJPLayanan.getText()
                }) == true) {
                    if (JenisPelayanan.getSelectedIndex() == 1) {
                        Sequel.mengedit("bridging_sep", "no_sep=?", "tglpulang=?", 2, new String[]{
                            Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + " " + TanggalSEP.getSelectedItem().toString().substring(11, 19),
                            response.asText()
                        });
                    }
                    JOptionPane.showMessageDialog(null, "Proses Selesai...!");

                    Sequel.mengedit3("skdp_bpjs", "no_rkm_medis=? and tanggal_datang=?", "status='Sudah Periksa'", 2, new String[]{
                        TNo.getText(), Valid.SetTgl(TanggalSEP.getSelectedItem() + "")
                    });
                    Sequel.queryu2("update booking_registrasi set status='Terdaftar' where no_rkm_medis=? and tanggal_periksa=?", 2, new String[]{
                        TNo.getText(), Valid.SetTgl(TanggalSEP.getSelectedItem() + "")
                    });
                    if (!prb.equals("")) {
                        if (Sequel.menyimpantf("bpjs_prb", "?,?", "PRB", 2, new String[]{response.asText(), prb}) == true) {
                            prb = "";
                        }
                    }
                    nosisrute = "";
                }

            } else {
                if (statuspasien.equals("Baru")) {
                    Sequel.meghapus3("pasien", "no_rkm_medis", TNo.getText());
                }
            }
        } catch (Exception ex) {
            if (statuspasien.equals("Baru")) {
                Sequel.meghapus3("pasien", "no_rkm_medis", TNo.getText());
            }
            System.out.println("Notifikasi Bridging : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void UpdateUmur() {
        Sequel.mengedit("pasien", "no_rkm_medis=?", "umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))", 1, new String[]{TNo.getText()});
    }

    public void isCek() {
        BtnSimpan.setEnabled(true);
        ppStatusFinger.setEnabled(true);
    }

    public void SetNoKartu(String NoPeserta) {
        emptTeks();
        NoKartu.setText(NoPeserta);
        if (Sequel.cariInteger("select count(pasien.no_peserta) from pasien where pasien.no_peserta='" + NoPeserta + "'") > 0) {
            tampil(NoPeserta);
        } else if (Sequel.cariInteger("select count(pasien.no_ktp) from pasien where pasien.no_ktp='" + NoPeserta + "'") > 0) {
            tampil(Sequel.cariIsi("select pasien.no_peserta from pasien where pasien.no_ktp='" + NoPeserta + "'"));
            NoKartu.setText(Sequel.cariIsi("select pasien.no_peserta from pasien where pasien.no_ktp='" + NoPeserta + "'"));
        } else {
            tampil(NoPeserta);
        }

        empt = true;
    }

    public void SetNoRujuk(String norujuk) {
        this.nosisrute = norujuk;
    }
}
