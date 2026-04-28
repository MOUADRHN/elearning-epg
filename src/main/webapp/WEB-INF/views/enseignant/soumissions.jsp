<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-enseignant.jsp" %>
<div class="topbar"><h5 class="mb-0">Soumissions - ${assignment.titre}</h5></div>
<div class="main-content"><div class="card"><div class="card-body">
  <table class="table align-middle">
    <thead class="table-dark"><tr><th>Etudiant</th><th>Soumis le</th><th>Fichier</th><th>Note</th><th>Action</th></tr></thead>
    <tbody>
      <c:forEach var="sub" items="${submissions}">
        <tr>
          <td>${sub.etudiant.prenom} ${sub.etudiant.nom}</td>
          <td>${sub.soumisLe}</td>
          <td><a href="/${sub.cheminFichier}" class="btn btn-outline-secondary btn-sm"><i class="bi bi-download"></i></a></td>
          <td><c:choose>
            <c:when test="${sub.note != null}"><span class="badge bg-success">${sub.note}/20</span></c:when>
            <c:otherwise><span class="badge bg-warning">Non corrige</span></c:otherwise>
          </c:choose></td>
          <td><button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modalCorr${sub.id}"><i class="bi bi-pen"></i> Corriger</button></td>
        </tr>
        <div class="modal fade" id="modalCorr${sub.id}" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
          <div class="modal-header"><h5 class="modal-title">Corriger - ${sub.etudiant.prenom}</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
          <form method="post" action="/enseignant/soumissions/${sub.id}/corriger">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="modal-body">
              <div class="mb-3"><label class="form-label">Note /20</label><input type="number" name="note" class="form-control" min="0" max="20" step="0.5" value="${sub.note}" required></div>
              <div class="mb-3"><label class="form-label">Feedback</label><textarea name="feedback" class="form-control" rows="3">${sub.feedback}</textarea></div>
            </div>
            <div class="modal-footer"><button type="submit" class="btn btn-primary">Enregistrer</button></div>
          </form>
        </div></div></div>
      </c:forEach>
    </tbody>
  </table>
</div></div></div>
<%@ include file="../layout/footer.jsp" %>