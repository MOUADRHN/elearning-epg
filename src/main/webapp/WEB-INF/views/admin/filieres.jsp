<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-admin.jsp" %>
<div class="topbar"><h5 class="mb-0">Filieres</h5>
  <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modalF"><i class="bi bi-plus"></i> Ajouter</button></div>
<div class="main-content"><div class="card"><div class="card-body">
  <table class="table table-hover"><thead class="table-dark"><tr><th>ID</th><th>Nom</th><th>Action</th></tr></thead>
    <tbody><c:forEach var="f" items="${filieres}"><tr><td>${f.id}</td><td>${f.nom}</td>
      <td><form method="post" action="/admin/filieres/${f.id}/supprimer" style="display:inline">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-danger btn-sm"><i class="bi bi-trash"></i></button></form></td></tr>
    </c:forEach></tbody>
  </table>
</div></div></div>
<div class="modal fade" id="modalF" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
  <div class="modal-header"><h5 class="modal-title">Nouvelle Filiere</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
  <form method="post" action="/admin/filieres">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="modal-body"><label class="form-label">Nom</label><input type="text" name="nom" class="form-control" required></div>
    <div class="modal-footer"><button type="submit" class="btn btn-primary">Creer</button></div>
  </form>
</div></div></div>
<%@ include file="../layout/footer.jsp" %>