<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="topbar">
  <h5 class="mb-0">${quiz.titre}</h5>
  <span class="badge bg-warning text-dark"><i class="bi bi-clock me-1"></i>${quiz.dureeMinutes} min</span>
</div>
<div class="main-content">
  <div class="card" style="max-width:700px;margin:auto"><div class="card-body">
    <form method="post" action="/etudiant/quiz/${quiz.id}/soumettre">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <c:forEach var="q" items="${quiz.questions}" varStatus="st">
        <div class="mb-4 p-3 border rounded">
          <p class="fw-semibold">${st.index+1}. ${q.contenu} <span class="badge bg-info ms-2">${q.points} pts</span></p>
          <div class="vstack gap-2">
            <label><input type="radio" name="q_${q.id}" value="A"> A</label>
            <label><input type="radio" name="q_${q.id}" value="B"> B</label>
            <label><input type="radio" name="q_${q.id}" value="C"> C</label>
            <label><input type="radio" name="q_${q.id}" value="D"> D</label>
          </div>
        </div>
      </c:forEach>
      <button type="submit" class="btn btn-success w-100 btn-lg"><i class="bi bi-check-circle me-2"></i>Soumettre</button>
    </form>
  </div></div>
</div>
<%@ include file="../layout/footer.jsp" %>
