<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="topbar"><h5 class="mb-0">Mes Cours</h5></div>
<div class="main-content">
  <c:if test="${not empty annonces}">
    <div class="mb-4">
      <h6 class="fw-bold text-muted mb-2">Annonces</h6>
      <c:forEach var="a" items="${annonces}">
        <div class="alert alert-info py-2"><strong>${a.titre}</strong> - ${a.contenu}</div>
      </c:forEach>
    </div>
  </c:if>
  <div class="row g-4">
    <c:forEach var="course" items="${courses}">
      <div class="col-md-4">
        <div class="card h-100"><div class="card-body">
          <h6 class="fw-bold">${course.titre}</h6>
          <p class="text-muted small">${course.description}</p>
          <div class="d-flex gap-2 mt-3">
          <a href="/etudiant/cours/${course.id}" class="btn btn-primary btn-sm flex-fill">acceder</a>
  <a href="/etudiant/cours/${course.id}/forum" class="btn btn-outline-secondary btn-sm"><i class="bi bi-chat-dots"></i></a>
            <a href="/etudiant/cours/${course.id}/boutique" class="btn btn-outline-warning btn-sm"><i class="bi bi-shop"></i></a>
          </div>
        </div></div>
      </div>
    </c:forEach>
    <c:if test="${empty courses}">
      <div class="alert alert-warning">Aucun cours disponible pour votre filiere.</div>
    </c:if>
  </div>
</div>
<%@ include file="../layout/footer.jsp" %>
