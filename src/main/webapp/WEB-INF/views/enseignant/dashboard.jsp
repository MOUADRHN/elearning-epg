<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-enseignant.jsp" %>
<div class="topbar"><h5 class="mb-0">Mes Cours</h5></div>
<div class="main-content"><div class="row g-4">
  <c:forEach var="course" items="${courses}">
    <div class="col-md-4"><div class="card h-100"><div class="card-body">
      <h6 class="fw-bold">${course.titre}</h6><p class="text-muted small">${course.description}</p>
      <a href="/enseignant/cours/${course.id}/chapitres" class="btn btn-primary btn-sm w-100 mt-2">
        <i class="bi bi-pencil-square"></i> Gerer le contenu</a>
          <a href="/enseignant/cours/${course.id}/forum" class="btn btn-outline-secondary btn-sm"><i class="bi bi-chat-dots"></i></a>
    </div></div></div>
  </c:forEach>
  <c:if test="${empty courses}"><div class="col-12"><div class="alert alert-info">Aucun cours assigne.</div></div></c:if>
</div></div>
<%@ include file="../layout/footer.jsp" %>