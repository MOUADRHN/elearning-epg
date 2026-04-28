<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>E-Learning EPG Fes</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
<style>
body { background:#f8f9fa; }
.sidebar { min-height:100vh; background:#2c3e50; color:white; width:240px; position:fixed; top:0; left:0; padding-top:20px; }
.sidebar a { color:#bdc3c7; text-decoration:none; display:block; padding:10px 20px; border-radius:6px; margin:2px 8px; }
.sidebar a:hover { background:#3d5166; color:white; }
.sidebar .brand { font-size:18px; font-weight:bold; color:white; padding:10px 20px 20px; border-bottom:1px solid #3d5166; margin-bottom:10px; }
.main-content { margin-left:240px; padding:24px; }
.topbar { background:white; border-bottom:1px solid #dee2e6; padding:12px 24px; margin-left:240px; position:sticky; top:0; z-index:100; display:flex; justify-content:space-between; align-items:center; }
.card { border:none; box-shadow:0 2px 8px rgba(0,0,0,0.08); border-radius:10px; }
.stat-card { border-left:4px solid; }
</style>
</head>
<body>
