<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar-admin.jsp" %>
<div class="topbar"><h5 class="mb-0">Dashboard Administrateur</h5></div>
<div class="main-content"><div class="row g-4">
  <div class="col-md-4"><div class="card stat-card p-4" style="border-color:#3498db">
    <div class="d-flex justify-content-between align-items-center">
      <div><div class="text-muted small">Utilisateurs</div><div class="fs-2 fw-bold">${totalUsers}</div></div>
      <i class="bi bi-people fs-1 text-primary"></i></div></div></div>
  <div class="col-md-4"><div class="card stat-card p-4" style="border-color:#27ae60">
    <div class="d-flex justify-content-between align-items-center">
      <div><div class="text-muted small">Cours</div><div class="fs-2 fw-bold">${totalCourses}</div></div>
      <i class="bi bi-book fs-1 text-success"></i></div></div></div>
  <div class="col-md-4"><div class="card stat-card p-4" style="border-color:#e74c3c">
    <div class="d-flex justify-content-between align-items-center">
      <div><div class="text-muted small">Filieres</div><div class="fs-2 fw-bold">${totalFilieres}</div></div>
      <i class="bi bi-diagram-3 fs-1 text-danger"></i></div></div></div>
</div></div>
<%@ include file="../layout/footer.jsp" %>