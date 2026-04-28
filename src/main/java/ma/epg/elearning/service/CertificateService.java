package ma.epg.elearning.service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import ma.epg.elearning.entity.*;
import ma.epg.elearning.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class CertificateService {
    private final ModuleScoreRepository moduleScoreRepository;
    private final CertificateRepository certificateRepository;
    private final EtudiantRepository etudiantRepository;

    @Transactional
    public void genererCertificat(Long courseId, String semestre) throws Exception {
        List<ModuleScore> classement = moduleScoreRepository.findClassementByCourse(courseId);
        if (classement.isEmpty()) return;
        Etudiant premier = classement.get(0).getEtudiant();
        String path = "uploads/certificats/certif_" + premier.getId() + "_" + semestre + ".pdf";
        genererPDF(premier, semestre, path);
        Certificate cert = new Certificate();
        cert.setEtudiant(premier); cert.setSemestre(semestre);
        cert.setCheminPDF(path); cert.setGenereLe(LocalDateTime.now());
        certificateRepository.save(cert);
    }

    private void genererPDF(Etudiant etudiant, String semestre, String path) throws Exception {
        new File("uploads/certificats").mkdirs();
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(path));
        doc.open();
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
        Font bodyFont  = new Font(Font.FontFamily.HELVETICA, 14);
        doc.add(new Paragraph("CERTIFICAT D'EXCELLENCE", titleFont));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Decerne a : " + etudiant.getPrenom() + " " + etudiant.getNom(), bodyFont));
        doc.add(new Paragraph("Semestre   : " + semestre, bodyFont));
        doc.add(new Paragraph("1er rang de la promotion.", bodyFont));
        doc.add(new Paragraph("Ecole Polytechnique des Genies — EPG Fes", bodyFont));
        doc.close();
    }
}
