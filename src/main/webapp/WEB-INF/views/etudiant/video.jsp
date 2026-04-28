<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-etudiant.jsp" %>
<div class="topbar">
  <h5 class="mb-0">${video.titre}</h5>
  <span class="badge bg-primary">${progress.pourcentageVu}% vu</span>
</div>
<div class="main-content">
  <div class="row g-4">
    <div class="col-md-8">
      <div class="card"><div class="card-body">
        <video id="videoPlayer" width="100%" controls style="border-radius:8px;background:#000">
          <source src="/${video.cheminFichier}" type="video/mp4">
        </video>
        <div class="progress mt-3" style="height:8px">
          <div class="progress-bar bg-primary" id="progressBar" style="width:${progress.pourcentageVu}%"></div>
        </div>
        <div class="text-muted small mt-1">${progress.pourcentageVu}% visionne</div>
      </div></div>
    </div>
    <div class="col-md-4">
      <div class="card"><div class="card-header fw-bold"><i class="bi bi-journal-text me-2"></i>Mes Notes</div>
        <div class="card-body">
          <c:forEach var="note" items="${notes}">
            <div class="mb-2 p-2 bg-light rounded">
              <small class="text-muted">${note.timestamp}s</small>
              <p class="mb-0 small">${note.contenu}</p>
            </div>
          </c:forEach>
          <form method="post" action="/etudiant/videos/${video.id}/notes" class="mt-3">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="timestamp" id="noteTimestamp" value="0">
            <textarea name="contenu" class="form-control form-control-sm mb-2" rows="2" placeholder="Note..."></textarea>
            <button type="submit" class="btn btn-outline-primary btn-sm w-100">+ Ajouter</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
const video = document.getElementById('videoPlayer');
const bar = document.getElementById('progressBar');
const tsInput = document.getElementById('noteTimestamp');
let lastSent = 0;
video.addEventListener('timeupdate', () => {
  const pct = Math.floor((video.currentTime / video.duration) * 100);
  bar.style.width = pct + '%';
  tsInput.value = Math.floor(video.currentTime);
  if (pct > lastSent + 10) {
    lastSent = pct;
    fetch('/etudiant/videos/${video.id}/progression', {
      method: 'POST',
      headers: {'Content-Type':'application/x-www-form-urlencoded','${_csrf.headerName}':'${_csrf.token}'},
      body: 'pourcentage=' + pct
    });
  }
});
</script>
<%@ include file="../layout/footer.jsp" %>
