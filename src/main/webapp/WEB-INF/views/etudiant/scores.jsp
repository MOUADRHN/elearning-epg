<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="topbar"><h5 class="mb-0">Mes Notes</h5></div>
<div class="main-content">
  <div class="card"><div class="card-body">
    <table class="table align-middle">
      <thead class="table-dark"><tr><th>Module</th><th>Points</th><th>Examen (80%)</th><th>Note Finale</th></tr></thead>
      <tbody>
        <c:forEach var="ms" items="${scores}">
          <tr>
            <td>${ms.course.titre}</td>
            <td><span class="badge bg-warning text-dark">${ms.pointsPlateforme} pts</span></td>
            <td><c:choose><c:when test="${ms.noteExamen != null}">${ms.noteExamen}/20</c:when><c:otherwise><span class="text-muted">-</span></c:otherwise></c:choose></td>
            <td><c:choose>
              <c:when test="${ms.noteFinale != null}"><span class="badge ${ms.noteFinale >= 10?'bg-success':'bg-danger'} fs-6">${ms.noteFinale}/20</span></c:when>
              <c:otherwise><span class="badge bg-secondary">En attente</span></c:otherwise>
            </c:choose></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div></div>
</div>
<%@ include file="../layout/footer.jsp" %>
