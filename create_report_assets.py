#!/usr/bin/env python3
"""
Generate clean, professional, academic figures (No flashy/colorful stuff):
1. Rate Comparison Chart (Clean Slate, Navy, Cool Gray academic tones)
2. Database Entity-Relationship / Table Schema Visual (Clean professional styling)
"""

from PIL import Image, ImageDraw, ImageFont
import os

font_path = "/System/Library/Fonts/Supplemental/Arial.ttf"
bold_font_path = "/System/Library/Fonts/Supplemental/Arial Bold.ttf"

font_title = ImageFont.truetype(bold_font_path, 18)
font_sub = ImageFont.truetype(font_path, 13)
font_label = ImageFont.truetype(bold_font_path, 12)
font_val = ImageFont.truetype(font_path, 11.5)

out_dir = "/Users/aarushdubey/Downloads/java_project/report_assets"
os.makedirs(out_dir, exist_ok=True)

# -------------------------------------------------------------
# FIGURE 1: Professional Rental Pricing Comparison Chart
# -------------------------------------------------------------
w, h = 800, 480
img1 = Image.new("RGB", (w, h), "#FFFFFF")
d1 = ImageDraw.Draw(img1)

# Subtle top bar
d1.rectangle([(0, 0), (w, 45)], fill="#1A2A3A")
d1.text((25, 12), "Polymorphic Rental Pricing Analysis across Duration", fill="#FFFFFF", font=font_title)

# Draw axes
origin_x, origin_y = 110, 400
d1.line([(origin_x, origin_y), (740, origin_y)], fill="#424242", width=1)
d1.line([(origin_x, origin_y), (origin_x, 80)], fill="#424242", width=1)

# Y-axis ticks & subtle grid
for i, price in enumerate([0, 200, 400, 600, 800, 1000]):
    y = origin_y - (price / 1000.0) * 280
    d1.line([(origin_x - 4, y), (origin_x, y)], fill="#424242", width=1)
    d1.line([(origin_x, y), (740, y)], fill="#F0F0F0", width=1)
    d1.text((origin_x - 50, y - 7), f"${price}", fill="#424242", font=font_val)

d1.text((origin_x - 65, 62), "Cost ($)", fill="#1A2A3A", font=font_label)

# Durations: 1 Day, 3 Days, 7 Days
durations = ["1 Day Rental", "3 Days Rental", "7 Days Rental"]

# Professional, clean, academic shades (No garish colors)
rates = {
    "Bike (0.8x)": ([24, 72, 168], "#78909C"),       # Cool Slate Gray
    "Car (1.0x)": ([50, 150, 350], "#1A2A3A"),        # Deep Navy
    "Truck (1.5x + tons)": ([140, 420, 980], "#455A64")# Dark Charcoal Slate
}

bar_width = 38
group_gap = 185

for g_idx, dur in enumerate(durations):
    group_x = origin_x + 60 + g_idx * group_gap
    d1.text((group_x + 10, origin_y + 15), dur, fill="#1A2A3A", font=font_label)
    
    for b_idx, (v_name, (costs, color)) in enumerate(rates.items()):
        cost = costs[g_idx]
        bar_h = (cost / 1000.0) * 280
        bx = group_x + b_idx * (bar_width + 8)
        by = origin_y - bar_h
        
        d1.rectangle([(bx, by), (bx + bar_width, origin_y)], fill=color, outline="#263238")
        d1.text((bx + 4, by - 16), f"${cost}", fill="#212121", font=font_val)

# Legend
leg_y = 58
for l_idx, (v_name, (_, color)) in enumerate(rates.items()):
    lx = 330 + l_idx * 135
    d1.rectangle([(lx, leg_y), (lx + 14, leg_y + 14)], fill=color, outline="#263238")
    d1.text((lx + 20, leg_y), v_name.split()[0], fill="#1A2A3A", font=font_val)

img1.save(f"{out_dir}/rate_chart.png")
print("Saved professional rate_chart.png")

# -------------------------------------------------------------
# FIGURE 2: Professional Database Schema Visual
# -------------------------------------------------------------
w, h = 800, 460
img2 = Image.new("RGB", (w, h), "#FFFFFF")
d2 = ImageDraw.Draw(img2)

d2.rectangle([(0, 0), (w, 45)], fill="#1A2A3A")
d2.text((25, 12), "Relational Database Schema: 'vehicles' Table in rentals.db", fill="#FFFFFF", font=font_title)

# Main Table Schema Box
d2.rounded_rectangle([(70, 75), (730, 425)], radius=6, fill="#FFFFFF", outline="#1A2A3A", width=1)
d2.rounded_rectangle([(70, 75), (730, 115)], radius=6, fill="#1A2A3A")
d2.text((90, 85), "TABLE: vehicles (Fleet Inventory)", fill="#FFFFFF", font=font_title)

columns = [
    ("vehicle_id", "TEXT", "PRIMARY KEY", "Unique registration ID (e.g. 'V001', 'V002')"),
    ("brand", "TEXT", "NOT NULL", "Manufacturer name (e.g. 'Toyota', 'Ford', 'Honda')"),
    ("model", "TEXT", "NOT NULL", "Model & subtype details (e.g. 'Camry', 'CBR', 'F-150')"),
    ("type", "TEXT", "NOT NULL", "Subclass discriminator ('Car', 'Bike', 'Truck')"),
    ("daily_rate", "REAL", "NOT NULL", "Base rental charge per day (e.g. 50.0, 30.0)"),
    ("is_rented", "INTEGER", "DEFAULT 0", "Availability flag: 0 = AVAILABLE, 1 = RENTED")
]

# Header Row
d2.rectangle([(70, 115), (730, 150)], fill="#F0F2F5")
d2.text((90, 125), "Column Name", fill="#1A2A3A", font=font_label)
d2.text((240, 125), "Data Type", fill="#1A2A3A", font=font_label)
d2.text((370, 125), "Constraints", fill="#1A2A3A", font=font_label)
d2.text((500, 125), "Description / Domain Role", fill="#1A2A3A", font=font_label)

for i, (col, dtype, constr, desc) in enumerate(columns):
    row_y = 150 + i * 44
    bg = "#FAFAFA" if i % 2 == 0 else "#FFFFFF"
    d2.rectangle([(70, row_y), (730, row_y + 44)], fill=bg, outline="#E0E0E0")
    
    d2.text((90, row_y + 14), col, fill="#1A2A3A", font=font_label)
    d2.text((240, row_y + 14), dtype, fill="#455A64", font=font_val)
    d2.text((370, row_y + 14), constr, fill="#455A64", font=font_val)
    d2.text((500, row_y + 14), desc, fill="#424242", font=font_val)

img2.save(f"{out_dir}/db_schema.png")
print("Saved professional db_schema.png")
