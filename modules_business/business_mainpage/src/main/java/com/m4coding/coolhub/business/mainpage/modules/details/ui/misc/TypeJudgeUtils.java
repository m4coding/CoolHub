package com.m4coding.coolhub.business.mainpage.modules.details.ui.misc;

/**
 * @author mochangsheng
 * @description 支持类型判断工具
 */

public class TypeJudgeUtils {

    // 判断是不是代码文件
    public static boolean isCodeTextFile(String fileName) {
        boolean res = false;
        // 文件的后缀
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            fileName = fileName.substring(index);
        }

        String codeFileSuffix[] = new String[] {
                        ".java",
                        ".confg",
                        ".ini",
                        ".xml",
                        ".json",
                        ".txt",
                        ".go",
                        ".php",
                        ".php3",
                        ".php4",
                        ".php5",
                        ".js",
                        ".css",
                        ".html",
                        ".properties",
                        ".c",
                        ".hpp",
                        ".h",
                        ".hh",
                        ".cpp",
                        ".cfg",
                        ".rb",
                        ".example",
                        ".gitignore",
                        ".project",
                        ".classpath",
                        ".m",
                        ".md",
                        ".rst",
                        ".vm",
                        ".cl",
                        ".py",
                        ".pl",
                        ".haml",
                        ".erb",
                        ".scss",
                        ".bat",
                        ".coffee",
                        ".as",
                        ".sh",
                        ".m",
                        ".pas",
                        ".cs",
                        ".groovy",
                        ".scala",
                        ".sql",
                        ".bas",
                        ".xml",
                        ".vb",
                        ".xsl",
                        ".swift",
                        ".ftl",
                        ".yml",
                        ".ru",
                        ".jsp",
                        ".markdown",
                        ".cshap",
                        ".apsx",
                        ".sass",
                        ".less",
                        ".ftl",
                        ".haml",
                        ".log",
                        ".tx",
                        ".csproj",
                        ".sln",
                        ".clj",
                        ".scm",
                        ".xhml",
                        ".xaml",
                        ".lua",
                        ".sty",
                        ".cls",
                        ".thm",
                        ".tex",
                        ".bst",
                        ".config",
                        "Podfile",
                        "Podfile.lock",
                        ".plist",
                        ".storyboard",
                        "gradlew",
                        ".gradle",
                        ".pro",
                        ".pbxproj",
                        ".xcscheme",
                        ".proto",
                        ".wxss",
                        ".wxml",
                        ".vi",
                        ".ctl",
                        ".ts",
                        ".kt",
                        ".vue"
                };

        for (String string : codeFileSuffix) {
            if (fileName.equalsIgnoreCase(string)) {
                res = true;
            }
        }

        // 特殊的文件
        String fileNames[] = new String[] {
                        "LICENSE", "TODO", "README", "readme", "makefile", "gemfile", "gemfile.*", "gemfile.lock", "CHANGELOG"
                };

        for (String string : fileNames) {
            if (fileName.equalsIgnoreCase(string)) {
                res = true;
            }
        }

        return res;
    }

    // 判断是否是图片
    public static boolean isImage(String fileName) {
        boolean res = false;
        // 图片后缀
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            fileName = fileName.substring(index);
        }
        String imageSuffix[] = new String[] {
                        ".png", "webp", ".jpg", ".jpeg", ".jpe", ".bmp", ".exif", ".dxf", ".wbmp", ".ico", ".jpe", ".gif", ".pcx", ".fpx", ".ufo", ".tiff", ".svg", ".eps", ".ai", ".tga", ".pcd", ".hdri"
                };
        for (String string : imageSuffix) {
            if (fileName.equalsIgnoreCase(string)) {
                res = true;
            }
        }
        return res;
    }

    public static boolean isMd(String fileName) {
        boolean res = false;

        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            fileName = fileName.substring(index);
        }

        String filesSuffix[] = new String[] {
                ".md"
        };

        for (String string : filesSuffix) {
            if (fileName.equalsIgnoreCase(string)) {
                res = true;
            }
        }

        // 特殊的文件
        String fileNames[] = new String[] {
                "README", "readme"
        };

        for (String string : fileNames) {
            if (fileName.equalsIgnoreCase(string)) {
                res = true;
            }
        }

        return res;
    }
}
