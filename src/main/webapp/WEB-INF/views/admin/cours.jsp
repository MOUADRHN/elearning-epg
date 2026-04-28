<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-admin.jsp" %>
<div class="topbar"><h5 class="mb-0">Cours</h5>
  <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modalC"><i class="bi bi-plus"></i> Nouveau</button></div>
<div class="main-content"><div class="row g-4">
  <c:forEach var="c" items="${courses}">
    <div class="col-md-4"><div class="card h-100"><div class="card-body">
      <h6 class="fw-bold">${c.titre}</h6><p class="text-muted small">${c.description}</p>
      <div class="d-flex gap-2 mt-3">
        <button class="btn btn-outline-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modalAff${c.id}"><i class="bi bi-link"></i> Affecter</button>
        <form method="post" action="/admin/cours/${c.id}/supprimer">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <button class="btn btn-outline-danger btn-sm"><i class="bi bi-trash"></i></button></form>
      </div>
    </div></div></div>
    <div class="modal fade" id="modalAff${c.id}" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
      <div class="modal-header"><h5 class="modal-title">Affecter - ${c.titre}</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
      <form method="post" action="/admin/cours/${c.id}/affecter">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="modal-body">
          <div class="mb-3"><label class="form-label">Filiere</label>
            <select name="filiereId" class="form-select" required>
              <c:forEach var="f" items="${filieres}"><option value="${f.id}">${f.nom}</option></c:forEach>
            </select></div>
          <div class="mb-3"><label class="form-label">Semestre</label>
            <select name="semestre" class="form-select" required>
              <c:forEach begin="1" end="6" var="s"><option value="${s}">S${s}</option></c:forEach>
            </select></div>
        </div>
        <div class="modal-footer"><button type="submit" class="btn btn-primary">Affecter</button></div>
      </form>
    </div></div></div>
  </c:forEach>
</div></div>
<div class="modal fade" id="modalC" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
  <div class="modal-header"><h5 class="modal-title">Nouveau Cours</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
  <form method="post" action="/admin/cours">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="modal-body">
      <div class="mb-3"><label class="form-label">Titre</label><input type="text" name="titre" class="form-control" required></div>
      <div class="mb-3"><label class="form-label">Description</label><textarea name="description" class="form-control" rows="3"></textarea></div>
    </div>
    <div class="modal-footer"><button type="submit" class="btn btn-primary">Creer</button></div>
  </form>
</div></div></div>
<%@ include file="../layout/footer.jsp" %>