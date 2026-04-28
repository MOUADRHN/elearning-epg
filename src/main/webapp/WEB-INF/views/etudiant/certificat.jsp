<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="main-content">
  <div class="card text-center" style="max-width:500px;margin:60px auto"><div class="card-body p-5">
    <c:choose>
      <c:when test="${certificat != null}">
        <div class="display-1">🏆</div>
        <h3 class="text-warning mt-3">Certificat d'Excellence</h3>
        <p class="text-muted">Semestre ${certificat.semestre}</p>
        <p class="text-muted small">Genere le ${certificat.genereLe}</p>
        <a href="/${certificat.cheminPDF}" class="btn btn-success mt-3" download>
          <i class="bi bi-download me-2"></i>Telecharger PDF
        </a>
      </c:when>
      <c:otherwise>
        <div class="display-1">🎯</div>
        <h3 class="text-muted mt-3">Pas encore de certificat</h3>
        <p class="text-muted">Le certificat est remis au 1er etudiant classe en fin de semestre.</p>
      </c:otherwise>
    </c:choose>
  </div></div>
</div>
<%@ include file="../layout/footer.jsp" %>
