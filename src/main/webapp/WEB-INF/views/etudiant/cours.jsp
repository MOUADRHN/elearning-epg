<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="topbar"><h5 class="mb-0">${course.titre}</h5></div>
<div class="main-content">
  <c:forEach var="ch" items="${chapters}">
    <div class="card mb-3">
      <div class="card-header fw-bold">
        <i class="bi bi-collection me-2"></i>${ch.titre}
      </div>
      <div class="card-body">
        <c:forEach var="v" items="${ch.videos}">
          <div class="d-flex align-items-center justify-content-between mb-2 p-2 bg-light rounded">
            <span><i class="bi bi-play-circle text-primary me-2"></i>${v.titre}</span>
            <div class="d-flex align-items-center gap-3">
              <span class="text-muted small">${v.duree} min</span>
              <a href="/etudiant/videos/${v.id}" class="btn btn-primary btn-sm">
                <i class="bi bi-play-fill"></i> Regarder
              </a>
            </div>
          </div>
        </c:forEach>
        <c:if test="${empty ch.videos}">
          <span class="text-muted small">Aucune vidéo disponible.</span>
        </c:if>
        <c:forEach var="quiz" items="${ch.quizzes}">
          <div class="d-flex align-items-center justify-content-between mb-2 p-2 bg-light rounded mt-2">
            <span><i class="bi bi-question-circle text-success me-2"></i>${quiz.titre}</span>
            <a href="/etudiant/quiz/${quiz.id}" class="btn btn-success btn-sm">
              <i class="bi bi-pencil"></i> Passer le quiz
            </a>
          </div>
        </c:forEach>
        <c:forEach var="devoir" items="${ch.assignments}">
          <div class="d-flex align-items-center justify-content-between mb-2 p-2 bg-light rounded mt-2">
            <span><i class="bi bi-file-earmark-text text-warning me-2"></i>${devoir.titre}</span>
            <div class="d-flex gap-2 align-items-center">
              <small class="text-muted">Limite : ${devoir.dateLimite}</small>
              <form method="post" action="/etudiant/devoirs/${devoir.id}/soumettre" enctype="multipart/form-data" class="d-flex gap-1">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="file" name="fichier" class="form-control form-control-sm" style="width:180px">
                <button type="submit" class="btn btn-warning btn-sm">Déposer</button>
              </form>
            </div>
          </div>
        </c:forEach>
      </div>
    </div>
  </c:forEach>
  <c:if test="${empty chapters}">
    <div class="alert alert-info">Aucun chapitre disponible pour ce cours.</div>
  </c:if>
</div>
<%@ include file="../layout/footer.jsp" %>