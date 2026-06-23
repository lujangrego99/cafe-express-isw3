#!/usr/bin/env python3
"""Genera el PDF del informe a partir del Markdown (markdown -> HTML -> WeasyPrint).

Uso:  python3 build_pdf.py
Salida: TP-Final-Integrador_CafeExpress.pdf
"""
import os
import markdown
from weasyprint import HTML

AQUI = os.path.dirname(os.path.abspath(__file__))
MD = os.path.join(AQUI, "TP-Final-Integrador_CafeExpress.md")
PDF = os.path.join(AQUI, "TP-Final-Integrador_CafeExpress.pdf")

CSS = """
@page { size: A4; margin: 2cm 2.2cm; }
body { font-family: 'Segoe UI', Arial, sans-serif; font-size: 11pt; color: #222; line-height: 1.45; }
h1 { color: #4a2c1e; font-size: 20pt; border-bottom: 3px solid #6f4e37; padding-bottom: 4px; }
h2 { color: #4a2c1e; font-size: 15pt; margin-top: 1.2em; border-bottom: 1px solid #e2d3bd; padding-bottom: 2px; }
h3 { color: #6f4e37; font-size: 12.5pt; margin-top: 1em; }
table { border-collapse: collapse; width: 100%; margin: 0.6em 0; font-size: 10pt; }
th, td { border: 1px solid #c9bba6; padding: 5px 8px; text-align: left; vertical-align: top; }
th { background: #f3e9da; }
img { max-width: 100%; display: block; margin: 0.6em auto; }
code { background: #f3efe9; padding: 1px 4px; border-radius: 3px; font-size: 9.5pt; }
pre { background: #f3efe9; padding: 10px; border-radius: 6px; overflow-x: auto; font-size: 9pt; }
pre code { background: none; padding: 0; }
blockquote { border-left: 4px solid #e2d3bd; margin: 0.6em 0; padding: 0.2em 0.9em; color: #555; }
"""

def main():
    with open(MD, encoding="utf-8") as f:
        texto = f.read()
    cuerpo = markdown.markdown(
        texto, extensions=["tables", "fenced_code", "sane_lists", "nl2br"]
    )
    html = f"<html><head><meta charset='utf-8'><style>{CSS}</style></head><body>{cuerpo}</body></html>"
    HTML(string=html, base_url=AQUI).write_pdf(PDF)
    print("PDF generado:", PDF)


if __name__ == "__main__":
    main()
