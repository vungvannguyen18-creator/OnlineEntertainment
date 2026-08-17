$srcDir = "d:\OnlineEntertainment\src"

Get-ChildItem -Path $srcDir -Include *.java, *.jsp -Recurse | ForEach-Object {
    $filePath = $_.FullName
    $content = Get-Content -Path $filePath -Raw -Encoding UTF8
    $originalContent = $content

    if ($filePath.EndsWith('.java')) {
        # Xóa block comments /* ... */
        $content = [System.Text.RegularExpressions.Regex]::Replace($content, '(?s)/\*.*?\*/', '')
        
        # Xóa single line comments // ... nhưng giữ lại nếu có dấu : phía trước (như http://)
        $lines = $content -split "`r`n|`n"
        $newLines = @()
        foreach ($line in $lines) {
            $newLine = [System.Text.RegularExpressions.Regex]::Replace($line, '(?<!:)//.*', '')
            if ([string]::IsNullOrWhiteSpace($newLine) -and -not [string]::IsNullOrWhiteSpace($line)) {
                continue
            }
            $newLines += $newLine
        }
        $content = $newLines -join "`r`n"
    } elseif ($filePath.EndsWith('.jsp')) {
        # Xóa JSP comments <%-- ... --%>
        $content = [System.Text.RegularExpressions.Regex]::Replace($content, '(?s)<%--.*?--%>', '')
        # Xóa HTML comments <!-- ... -->
        $content = [System.Text.RegularExpressions.Regex]::Replace($content, '(?s)<!--.*?-->', '')
    }

    if ($content -ne $originalContent) {
        Set-Content -Path $filePath -Value $content -Encoding UTF8
        Write-Output "Cleaned: $filePath"
    }
}
