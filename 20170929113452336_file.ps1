Param(
  [string]$WebApplication = "",
  [string]$test = "",
  [string]$owner = "",
  [string]$ENV,
  [switch]$BooleanParam
)
Write-Host "$WebApplication;$test;$ENV;$owner;$BooleanParam"