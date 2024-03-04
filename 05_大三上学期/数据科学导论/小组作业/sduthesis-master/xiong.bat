del *.aux /s
del *.bak /s
del *.log /s

del *.bbl /s
del *.dvi /s
del *.blg /s
del *.thm /s
del *.toc /s
del *.toe /s

del *.lof 
del *.lot
del *.out 

del *.fen
del *.ten
del *.ps

del *.loa

set ARTICLE=sdumain
latex -synctex=1 %ARTICLE%
bibtex %ARTICLE%
latex -synctex=1 %ARTICLE%
latex -synctex=1 %ARTICLE%
dvipdfmx  %ARTICLE%.dvi


del *.aux /s
del *.bak /s
del *.log /s

del *.bbl /s
del *.dvi /s
del *.blg /s
del *.thm /s
del *.toc /s
del *.toe /s

del *.lof 
del *.lot
del *.out 

del *.fen
del *.ten
del *.ps

del *.gz
del *.gz(busy)
del *.synctex

del *.loa
del *.pdf
del *.aux /s
del *.bak /s
del *.log /s

del *.bbl /s
del *.dvi /s
del *.blg /s
del *.thm /s
del *.toc /s
del *.toe /s

del *.lof 
del *.lot
del *.out 

del *.fen
del *.ten
del *.ps

del *.gz
del *.gz(busy)
del *.synctex

del *.loa

set ARTICLE=sdumain
latex %ARTICLE%.tex
bibtex %ARTICLE%
latex %ARTICLE%.tex
latex %ARTICLE%.tex
del *.aux /s
del *.bak /s
del *.log /s

del *.bbl /s
del *.dvi /s
del *.blg /s
del *.thm /s
del *.toc /s
del *.toe /s

del *.lof 
del *.lot
del *.out 

del *.fen
del *.ten
del *.ps

del *.loa

set ARTICLE=sdumain
latex -synctex=1 %ARTICLE%
bibtex %ARTICLE%
latex -synctex=1 %ARTICLE%
latex -synctex=1 %ARTICLE%
dvipdfmx  %ARTICLE%.dvi
start %ARTICLE%.pdf

