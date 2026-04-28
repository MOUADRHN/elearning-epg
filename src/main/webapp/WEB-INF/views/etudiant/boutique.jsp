<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="topbar">
  <h5 class="mb-0">Boutique</h5>
  <span class="badge bg-warning text-dark fs-6"><i class="bi bi-star-fill me-1"></i>${pointsSolde} points</span>
</div>
<div class="main-content">
  <div class="row g-4">
    <c:forEach var="item" items="${items}">
      <div class="col-md-4">
        <div class="card h-100"><div class="card-body text-center">
          <i class="bi bi-gift fs-1 text-warning"></i>
          <h6 class="mt-2 fw-bold">${item.titre}</h6>
          <span class="badge bg-warning text-dark mb-3">${item.coutPoints} points</span>
          <form method="post" action="/etudiant/boutique/${item.id}/acheter">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary w-100"
              ${pointsSolde < item.coutPoints ? 'disabled' : ''}>Acheter</button>
          </form>
        </div></div>
      </div>
    </c:forEach>
    <c:if test="${empty items}">
      <div class="alert alert-info">Aucun item disponible.</div>
    </c:if>
  </div>
</div>
<%@ include file="../layout/footer.jsp" %>
