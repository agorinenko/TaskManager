Param(
  [string]$WebApplication = "http://portal/",
  [string]$test = "test",
  [string]$owner = "test\agorinenko",
  [string]$ENV,
  [switch]$BooleanParam
)
Write-Host "$WebApplication;$test;$ENV;$owner;$BooleanParam"