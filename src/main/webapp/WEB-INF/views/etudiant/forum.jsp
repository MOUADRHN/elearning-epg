<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="topbar">
  <h5 class="mb-0">Forum - ${course.titre}</h5>
  <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modalPost">+ Post</button>
</div>
<div class="main-content">
  <c:forEach var="post" items="${posts}">
    <div class="card mb-3">
      <div class="card-header d-flex justify-content-between">
        <strong>${post.titre}</strong>
        <small class="text-muted">${post.auteur.prenom} - ${post.creeLe}</small>
      </div>
      <div class="card-body">
        <p>${post.contenu}</p>
        <c:forEach var="reply" items="${post.replies}">
          <div class="ms-4 p-2 bg-light rounded mb-2">
            <small class="text-muted">${reply.auteur.prenom} :</small>
            <p class="mb-0 small">${reply.contenu}</p>
          </div>
        </c:forEach>
        <form method="post" action="/etudiant/forum/${post.id}/repondre" class="mt-2 d-flex gap-2">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <input type="text" name="contenu" class="form-control form-control-sm" placeholder="Votre reponse...">
          <button type="submit" class="btn btn-outline-primary btn-sm"><i class="bi bi-send"></i></button>
        </form>
      </div>
    </div>
  </c:forEach>
</div>
<div class="modal fade" id="modalPost" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
  <div class="modal-header"><h5 class="modal-title">Nouveau Post</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
  <form method="post" action="/etudiant/cours/${course.id}/forum">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="modal-body">
      <div class="mb-3"><label class="form-label">Titre</label><input type="text" name="titre" class="form-control" required></div>
      <div class="mb-3"><label class="form-label">Contenu</label><textarea name="contenu" class="form-control" rows="4"></textarea></div>
    </div>
    <div class="modal-footer"><button type="submit" class="btn btn-primary">Publier</button></div>
  </form>
</div></div></div>
<%@ include file="../layout/footer.jsp" %>
