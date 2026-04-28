<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-admin.jsp" %>
<div class="topbar"><h5 class="mb-0">Utilisateurs</h5>
  <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modalEtu"><i class="bi bi-plus"></i> Ajouter</button></div>
<div class="main-content"><div class="card"><div class="card-body">
  <table class="table table-hover align-middle">
    <thead class="table-dark"><tr><th>Nom</th><th>Email</th><th>Role</th><th>Statut</th><th>Action</th></tr></thead>
    <tbody>
      <c:forEach var="u" items="${users}">
        <tr><td>${u.prenom} ${u.nom}</td><td>${u.email}</td>
          <td><span class="badge ${u.role=='ADMIN'?'bg-danger':u.role=='ENSEIGNANT'?'bg-primary':'bg-success'}">${u.role}</span></td>
          <td><span class="badge ${u.actif?'bg-success':'bg-secondary'}">${u.actif?'Actif':'Inactif'}</span></td>
          <td><c:if test="${u.actif}">
            <form method="post" action="/admin/utilisateurs/${u.id}/desactiver" style="display:inline">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <button class="btn btn-warning btn-sm">Desactiver</button></form></c:if></td></tr>
      </c:forEach>
    </tbody>
  </table>
</div></div></div>
<div class="modal fade" id="modalEtu" tabindex="-1"><div class="modal-dialog"><div class="modal-content">
  <div class="modal-header"><h5 class="modal-title">Creer Etudiant</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
  <form method="post" action="/admin/utilisateurs/etudiant">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="modal-body"><div class="row g-3">
      <div class="col-6"><label class="form-label">Nom</label><input type="text" name="nom" class="form-control" required></div>
      <div class="col-6"><label class="form-label">Prenom</label><input type="text" name="prenom" class="form-control" required></div>
      <div class="col-12"><label class="form-label">Email</label><input type="email" name="email" class="form-control" required></div>
      <div class="col-12"><label class="form-label">Mot de passe</label><input type="password" name="motDePasse" class="form-control" required></div>
      <div class="col-6"><label class="form-label">CNE</label><input type="text" name="cne" class="form-control" required></div>
      <div class="col-6"><label class="form-label">Semestre</label><input type="number" name="semestre" class="form-control" min="1" max="6" required></div>
    </div></div>
    <div class="modal-footer"><button type="submit" class="btn btn-primary">Creer</button></div>
  </form>
</div></div></div>
<%@ include file="../layout/footer.jsp" %>