<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="main-content">
  <div class="card text-center" style="max-width:500px;margin:60px auto"><div class="card-body p-5">
    <c:choose>
      <c:when test="${result.score >= 10}">
        <div class="display-1">🎉</div><h3 class="text-success mt-3">Felicitations !</h3>
      </c:when>
      <c:otherwise>
        <div class="display-1">📚</div><h3 class="text-warning mt-3">Continuez !</h3>
      </c:otherwise>
    </c:choose>
    <div class="display-4 fw-bold mt-3">${result.score}/20</div>
    <p class="text-muted mt-2"><i class="bi bi-star-fill text-warning me-1"></i>+${result.pointsGagnes} points</p>
    <a href="/etudiant/dashboard" class="btn btn-primary mt-3">Retour au Dashboard</a>
  </div></div>
</div>
<%@ include file="../layout/footer.jsp" %>
