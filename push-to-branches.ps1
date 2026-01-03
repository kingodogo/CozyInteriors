param(
    [Parameter(Mandatory=$true)]
    [string]$FilePath,
    
    [Parameter(Mandatory=$true)]
    [string]$CommitMessage
)

$currentBranch = git branch --show-current

if ($FilePath -match "Versions[\\/]([^\\/]+)[\\/].*") {
    $version = $matches[1]
    
    Write-Host "Detected version: $version"
    Write-Host "File path: $FilePath"
    
    git add $FilePath
    git commit -m $CommitMessage
    git push origin Dev
    
    $versionBranchExists = git branch -a | Select-String "^\s*$version$|remotes/origin/$version$"
    
    if (-not $versionBranchExists) {
        Write-Host "Creating branch $version"
        git checkout -b $version
    } else {
        Write-Host "Switching to branch $version"
        git checkout $version
    }
    
    $relativePath = $FilePath -replace "Versions[\\/]$version[\\/]", ""
    $targetPath = $relativePath
    
    $targetDir = Split-Path $targetPath -Parent
    if ($targetDir -and -not (Test-Path $targetDir)) {
        New-Item -ItemType Directory -Force -Path $targetDir | Out-Null
    }
    
    Copy-Item -Path $FilePath -Destination $targetPath -Force
    
    git add $targetPath
    $versionCommitMsg = $CommitMessage -replace "Versions[\\/]$version[\\/]", ""
    git commit -m $versionCommitMsg
    git push origin $version
    
    Write-Host "Switching back to Dev"
    git checkout Dev
    
    Write-Host "Done! Pushed to Dev and $version branches"
} else {
    Write-Host "File is not in a Versions/ folder, only committing to Dev"
    git add $FilePath
    git commit -m $CommitMessage
    git push origin Dev
}
