<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="topbar"><h5 class="mb-0">Mon Profil</h5></div>
<div class="main-content">
  <div class="card" style="max-width:600px;margin:auto">
    <div class="card-body p-4">

      <c:if test="${param.success}">
        <div class="alert alert-success">Profil mis à jour avec succès.</div>
      </c:if>

      <div class="text-center mb-4">
       <div style="width:80px;height:80px;background:#3498db;border-radius:50%;
                   display:flex;align-items:center;justify-content:center;
                   font-size:32px;color:white;margin:auto">
           <i class="bi bi-person-fill"></i>
       </div>
        <h5 class="mt-3">${user.prenom} ${user.nom}</h5>
        <span class="badge bg-success">ETUDIANT</span>
      </div>

      <form method="post" action="/etudiant/profil">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="row g-3">
          <div class="col-6">
            <label class="form-label">Nom</label>
            <input type="text" name="nom" class="form-control" value="${user.nom}" required>
          </div>
          <div class="col-6">
            <label class="form-label">Prénom</label>
            <input type="text" name="prenom" class="form-control" value="${user.prenom}" required>
          </div>
          <div class="col-12">
            <label class="form-label">Email</label>
            <input type="email" class="form-control" value="${user.email}" disabled>
          </div>
          <div class="col-6">
            <label class="form-label">CNE</label>
            <input type="text" class="form-control" value="${user.cne}" disabled>
          </div>
          <div class="col-6">
            <label class="form-label">Semestre</label>
            <input type="text" class="form-control" value="S${user.semestre}" disabled>
          </div>
          <div class="col-12">
            <label class="form-label">Nouveau mot de passe <span class="text-muted small">(laisser vide = pas de changement)</span></label>
            <input type="password" name="nouveauMdp" class="form-control" placeholder="••••••••">
          </div>
          <div class="col-12 mt-2">
            <button type="submit" class="btn btn-primary w-100">
              <i class="bi bi-save me-2"></i>Enregistrer
            </button>
          </div>
        </div>
      </form>

      <hr class="mt-4">
      <div class="row text-center">
        <div class="col-4">
          <div class="fw-bold text-primary fs-4">${user.pointsSolde}</div>
          <div class="text-muted small">Points solde</div>
        </div>
        <div class="col-4">
          <div class="fw-bold text-success fs-4">S${user.semestre}</div>
          <div class="text-muted small">Semestre</div>
        </div>
        <div class="col-4">
          <div class="fw-bold text-warning fs-4">${user.filiere.nom}</div>
          <div class="text-muted small">Filière</div>
        </div>
      </div>
    </div>
  </div>
</div>
<%@ include file="../layout/footer.jsp" %>