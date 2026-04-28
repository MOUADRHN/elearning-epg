<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="fr"><head><meta charset="UTF-8"><title>Connexion EPG</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>body{background:linear-gradient(135deg,#2c3e50,#3498db);min-height:100vh;display:flex;align-items:center;justify-content:center}
.login-card{background:white;border-radius:16px;padding:40px;width:400px;box-shadow:0 20px 60px rgba(0,0,0,.3)}</style></head><body>
<div class="login-card">
  <div class="text-center mb-4"><div style="font-size:22px;font-weight:bold;color:#2c3e50">EPG E-Learning</div>
  <p class="text-muted mt-1">Ecole Polytechnique des Genies - Fes</p></div>
  <c:if test="${param.error}"><div class="alert alert-danger">Email ou mot de passe incorrect.</div></c:if>
  <c:if test="${param.logout}"><div class="alert alert-success">Deconnexion reussie.</div></c:if>
  <form method="post" action="/login">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div class="mb-3"><label class="form-label fw-semibold">Email</label>
    <input type="email" name="username" class="form-control form-control-lg" placeholder="votre@email.com" required autofocus></div>
    <div class="mb-4"><label class="form-label fw-semibold">Mot de passe</label>
    <input type="password" name="password" class="form-control form-control-lg" placeholder="..." required></div>
    <button type="submit" class="btn btn-primary w-100 btn-lg">Se connecter</button>
  </form>
</div></body></html>