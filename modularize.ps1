$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

function Move-Dir($src, $dst) {
    if (Test-Path $src) {
        $parent = Split-Path $dst -Parent
        if (-not (Test-Path $parent)) { New-Item -ItemType Directory -Path $parent -Force | Out-Null }
        Copy-Item -Path $src -Destination $dst -Recurse -Force
        Remove-Item -Path $src -Recurse -Force
        Write-Host "  Moved: $src -> $dst"
    } else {
        Write-Host "  Skip (not found): $src"
    }
}

function Move-File($src, $dst) {
    if (Test-Path $src) {
        $parent = Split-Path $dst -Parent
        if (-not (Test-Path $parent)) { New-Item -ItemType Directory -Path $parent -Force | Out-Null }
        Copy-Item -Path $src -Destination $dst -Force
        Remove-Item -Path $src -Force
        Write-Host "  Moved: $src -> $dst"
    } else {
        Write-Host "  Skip (not found): $src"
    }
}

# ============================================================
# MODULE: datastructures
# ============================================================
Write-Host "`n=== MODULE: datastructures ==="

# commonMain - datastructures package
Move-Dir "src\commonMain\kotlin\br\uem\din\datastructures" "datastructures\src\commonMain\kotlin\br\uem\din\datastructures"

# commonMain - extensions/MutableList.kt (swap) -> datastructures module, keep package br.uem.din.extensions
Move-File "src\commonMain\kotlin\br\uem\din\extensions\MutableList.kt" "datastructures\src\commonMain\kotlin\br\uem\din\extensions\MutableList.kt"

# commonMain - extensions/TreeTraversalExtensions.kt -> datastructures module (package is datastructures.tree)
Move-File "src\commonMain\kotlin\br\uem\din\extensions\TreeTraversalExtensions.kt" "datastructures\src\commonMain\kotlin\br\uem\din\datastructures\tree\TreeTraversalExtensions.kt"

# commonMain - extensions/GraphExtensions.kt -> datastructures module (only depends on datastructures.graph)
Move-File "src\commonMain\kotlin\br\uem\din\extensions\GraphExtensions.kt" "datastructures\src\commonMain\kotlin\br\uem\din\extensions\GraphExtensions.kt"

# commonTest - datastructures tests
Move-Dir "src\commonTest\kotlin\br\uem\din\datastructures" "datastructures\src\commonTest\kotlin\br\uem\din\datastructures"

# Platform-specific sources (jvmMain, jsMain, nativeMain, jvmTest, jsTest, nativeTest)
foreach ($sourceSet in @("jvmMain","jsMain","nativeMain","jvmTest","jsTest","nativeTest")) {
    Move-Dir "src\$sourceSet\kotlin\br\uem\din\datastructures" "datastructures\src\$sourceSet\kotlin\br\uem\din\datastructures"
}

# ============================================================
# MODULE: algorithms
# ============================================================
Write-Host "`n=== MODULE: algorithms ==="

# commonMain - algorithms package
Move-Dir "src\commonMain\kotlin\br\uem\din\algorithms" "algorithms\src\commonMain\kotlin\br\uem\din\algorithms"

# commonTest - algorithms tests
Move-Dir "src\commonTest\kotlin\br\uem\din\algorithms" "algorithms\src\commonTest\kotlin\br\uem\din\algorithms"

# ============================================================
# MODULE: extensions
# ============================================================
Write-Host "`n=== MODULE: extensions ==="

# commonMain - remaining extension files (CollectionExtensions, MathExtensions, StringExtensions)
# Note: MutableList.kt and GraphExtensions.kt already moved to datastructures
Move-Dir "src\commonMain\kotlin\br\uem\din\extensions" "extensions\src\commonMain\kotlin\br\uem\din\extensions"

# commonTest - extensions tests
Move-Dir "src\commonTest\kotlin\br\uem\din\extensions" "extensions\src\commonTest\kotlin\br\uem\din\extensions"

# ============================================================
# MODULE: optimization
# ============================================================
Write-Host "`n=== MODULE: optimization ==="

# commonMain - optimization package
Move-Dir "src\commonMain\kotlin\br\uem\din\optimization" "optimization\src\commonMain\kotlin\br\uem\din\optimization"

# commonTest - optimization tests
Move-Dir "src\commonTest\kotlin\br\uem\din\optimization" "optimization\src\commonTest\kotlin\br\uem\din\optimization"

# ============================================================
# MODULE: bom (no sources, just build.gradle.kts)
# ============================================================
Write-Host "`n=== MODULE: bom ==="
New-Item -ItemType Directory -Path "bom" -Force | Out-Null
Write-Host "  Created: bom/"

# ============================================================
# Verify old src is empty
# ============================================================
Write-Host "`n=== Verification ==="
$remaining = Get-ChildItem -Path "src" -Recurse -File -Filter "*.kt" -ErrorAction SilentlyContinue
if ($remaining) {
    Write-Host "  WARNING: Remaining .kt files in src/:"
    $remaining | ForEach-Object { Write-Host "    $($_.FullName)" }
} else {
    Write-Host "  OK: No .kt files remaining in src/"
}

Write-Host "`nDone!"
