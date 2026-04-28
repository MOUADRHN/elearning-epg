<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-enseignant.jsp" %>
<div class="topbar"><h5 class="mb-0">${course.titre} - Chapitres</h5>
  <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modalCh"><i class="bi bi-plus"></i> Chapitre</button></div>
<div class="main-content">
  <c:forEach var="ch" items="${chapters}">
    <div class="card mb-3">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span class="fw-bold"><i class="bi bi-collection me-2"></i>${ch.titre}</span>
        <div class="d-flex gap-2">
          <button class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modalVid${ch.id}"><i class="bi bi-camera-video"></i> Video</button>
          <button class="btn btn-outline-success btn-sm" data-bs-toggle="modal" data-bs-target="#modalQuiz${ch.id}"><i class="bi bi-question-circle"></i> Quiz</button>
          <button class="btn btn-outline-warning btn-sm" data-bs-toggle="modal" data-bs-target="#modalDev${ch.id}"><i class="bi bi-file-earmark-text"></i> Devoir</button>
        </div>
      </div>
      <div class="card-body">
        <c:forEach var="v" items="${ch.videos}">
          <div class="d-flex align-items-center gap-2 mb-2">
            <i class="bi bi-play-circle text-primary"></i><span>${v.titre}</span><span class="text-muted small">(${v.duree} min)</span>
          </div>
        </c:forEach>
        <c:if test="${empty ch.videos}"><span class="text-muted small">Aucune video.</span></c:if>
      </div>
    </div>
    <div class="modal fade" id="modalVid${ch.id}" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
      <div class="modal-header"><h5 class="modal-title">Ajouter Video</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
      <form method="post" action="/enseignant/chapitres/${ch.id}/videos" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="modal-body">
          <div class="mb-3"><label class="form-label">Titre</label><input type="text" name="titre" class="form-control" required></div>
          <div class="mb-3"><label class="form-label">Duree (min)</label><input type="number" name="duree" class="form-control" min="1"></div>
          <div class="mb-3"><label class="form-label">Fichier video</label><input type="file" name="fichier" class="form-control" accept="video/*"></div>
        </div>
        <div class="modal-footer"><button type="submit" class="btn btn-primary">Uploader</button></div>
      </form>
    </div></div></div>
    <div class="modal fade" id="modalQuiz${ch.id}" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
      <div class="modal-header"><h5 class="modal-title">Creer Quiz</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
      <form method="post" action="/enseignant/chapitres/${ch.id}/quiz">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="modal-body">
          <div class="mb-3"><label class="form-label">Titre</label><input type="text" name="titre" class="form-control" required></div>
          <div class="mb-3"><label class="form-label">Duree (min)</label><input type="number" name="dureeMinutes" class="form-control" min="5"></div>
        </div>
        <div class="modal-footer"><button type="submit" class="btn btn-success">Creer</button></div>
      </form>
    </div></div></div>
    <div class="modal fade" id="modalDev${ch.id}" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
      <div class="modal-header"><h5 class="modal-title">Creer Devoir</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
      <form method="post" action="/enseignant/chapitres/${ch.id}/devoirs">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="modal-body">
          <div class="mb-3"><label class="form-label">Titre</label><input type="text" name="titre" class="form-control" required></div>
          <div class="mb-3"><label class="form-label">Consigne</label><textarea name="consigne" class="form-control" rows="3"></textarea></div>
          <div class="mb-3"><label class="form-label">Date limite</label><input type="datetime-local" name="dateLimite" class="form-control" required></div>
        </div>
        <div class="modal-footer"><button type="submit" class="btn btn-warning">Creer</button></div>
      </form>
    </div></div></div>
  </c:forEach>
</div>
<div class="modal fade" id="modalCh" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
  <div class="modal-header"><h5 class="modal-title">Nouveau Chapitre</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
  <form method="post" action="/enseignant/cours/${course.id}/chapitres">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="modal-body"><label class="form-label">Titre</label><input type="text" name="titre" class="form-control" required></div>
    <div class="modal-footer"><button type="submit" class="btn btn-primary">Ajouter</button></div>
  </form>
</div></div></div>
<%@ include file="../layout/footer.jsp" %>