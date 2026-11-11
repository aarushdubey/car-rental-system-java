#!/usr/bin/env python3
"""
Generate high-resolution visual figures for the project report:
1. Rate Comparison Chart (Car vs Bike vs Truck across 1, 3, 7 days)
2. Thread Synchronization Latency & Queue Performance Chart
3. Database Entity-Relationship / Table Schema Visual
"""

from PIL import Image, ImageDraw, ImageFont
import os

font_path = "/System/Library/Fonts/Supplemental/Arial.ttf"
bold_font_path = "/System/Library/Fonts/Supplemental/Arial Bold.ttf"

font_title = ImageFont.truetype(bold_font_path, 20)
font_sub = ImageFont.truetype(font_path, 14)
font_label = ImageFont.truetype(bold_font_path, 13)
font_val = ImageFont.truetype(font_path, 12)

out_dir = "/Users/aarushdubey/Downloads/java_project/report_assets"
os.makedirs(out_dir, exist_ok=True)

# -------------------------------------------------------------
# FIGURE 1: Rental Pricing Comparison Chart (Bar Chart)
# -------------------------------------------------------------
w, h = 800, 480
img1 = Image.new("RGB", (w, h), "#FAFCFF")
d1 = ImageDraw.Draw(img1)

# Title
d1.rectangle([(0, 0), (w, 50)], fill="#1A2A3A")
d1.text((30, 15), "Polymorphic Rental Pricing Analysis across Duration", fill="#FFFFFF", font=font_title)

# Draw axes
origin_x, origin_y = 120, 400
d1.line([(origin_x, origin_y), (740, origin_y)], fill="#546E7A", width=2)
d1.line([(origin_x, origin_y), (origin_x, 90)], fill="#546E7A", width=2)

# Y-axis ticks & grid
for i, price in enumerate([0, 200, 400, 600, 800, 1000]):
    y = origin_y - (price / 1000.0) * 280
    d1.line([(origin_x - 5, y), (origin_x, y)], fill="#546E7A", width=2)
    d1.line([(origin_x, y), (740, y)], fill="#ECEFF1", width=1)
    d1.text((origin_x - 55, y - 8), f"${price}", fill="#546E7A", font=font_val)

d1.text((origin_x - 70, 70), "Cost ($)", fill="#1A2A3A", font=font_label)

# Durations: 1 Day, 3 Days, 7 Days
durations = ["1 Day Rental", "3 Days Rental", "7 Days Rental"]
rates = {
    "Bike (0.8x)": ([24, 72, 168], "#009688"),
    "Car (1.0x)": ([50, 150, 350], "#1E88E5"),
    "Truck (1.5x + tons)": ([140, 420, 980], "#E65100")
}

bar_width = 38
group_gap = 180

for g_idx, dur in enumerate(durations):
    group_x = origin_x + 60 + g_idx * group_gap
    d1.text((group_x + 10, origin_y + 15), dur, fill="#1A2A3A", font=font_label)
    
    for b_idx, (v_name, (costs, color)) in enumerate(rates.items()):
        cost = costs[g_idx]
        bar_h = (cost / 1000.0) * 280
        bx = group_x + b_idx * (bar_width + 8)
        by = origin_y - bar_h
        
        d1.rectangle([(bx, by), (bx + bar_width, origin_y)], fill=color, outline="#334E68")
        d1.text((bx + 4, by - 16), f"${cost}", fill="#1A2A3A", font=font_val)

# Legend
leg_y = 65
for l_idx, (v_name, (_, color)) in enumerate(rates.items()):
    lx = 320 + l_idx * 145
    d1.rectangle([(lx, leg_y), (lx + 15, leg_y + 15)], fill=color, outline="#334E68")
    d1.text((lx + 22, leg_y), v_name.split()[0], fill="#1A2A3A", font=font_val)

img1.save(f"{out_dir}/rate_chart.png")
print("Saved rate_chart.png")

# -------------------------------------------------------------
# FIGURE 2: Database Schema & Entity Structure
# -------------------------------------------------------------
w, h = 800, 460
img2 = Image.new("RGB", (w, h), "#FAFCFF")
d2 = ImageDraw.Draw(img2)

d2.rectangle([(0, 0), (w, 50)], fill="#1A2A3A")
d2.text((30, 15), "Relational Database Schema: 'vehicles' Table in rentals.db", fill="#FFFFFF", font=font_title)

# Main Table Schema Box
d2.rounded_rectangle([(80, 80), (720, 420)], radius=8, fill="#FFFFFF", outline="#1A2A3A", width=2)
d2.rounded_rectangle([(80, 80), (720, 125)], radius=8, fill="#1A2A3A")
d2.text((100, 92), "TABLE: vehicles (Primary Storage for Active Fleet)", fill="#FFFFFF", font=font_title)

columns = [
    ("vehicle_id", "TEXT", "PRIMARY KEY", "Unique registration ID (e.g. 'V001', 'V002')"),
    ("brand", "TEXT", "NOT NULL", "Manufacturer name (e.g. 'Toyota', 'Ford', 'Honda')"),
    ("model", "TEXT", "NOT NULL", "Model & subtype details (e.g. 'Camry', 'CBR', 'F-150')"),
    ("type", "TEXT", "NOT NULL", "Subclass discriminator ('Car', 'Bike', 'Truck')"),
    ("daily_rate", "REAL", "NOT NULL", "Base rental charge per day (e.g. 50.0, 30.0)"),
    ("is_rented", "INTEGER", "DEFAULT 0", "Availability flag: 0 = AVAILABLE, 1 = RENTED")
]

# Header Row
d2.rectangle([(80, 125), (720, 160)], fill="#ECEFF1")
d2.text((100, 133), "Column Name", fill="#1A2A3A", font=font_label)
d2.text((250, 133), "Data Type", fill="#1A2A3A", font=font_label)
d2.text((380, 133), "Constraints", fill="#1A2A3A", font=font_label)
d2.text((510, 133), "Description / Domain Role", fill="#1A2A3A", font=font_label)

for i, (col, dtype, constr, desc) in enumerate(columns):
    row_y = 160 + i * 42
    bg = "#FAFCFF" if i % 2 == 0 else "#FFFFFF"
    d2.rectangle([(80, row_y), (720, row_y + 42)], fill=bg, outline="#CFD8DC")
    
    d2.text((100, row_y + 12), col, fill="#009688", font=font_label)
    d2.text((250, row_y + 12), dtype, fill="#1E88E5", font=font_val)
    d2.text((380, row_y + 12), constr, fill="#E65100", font=font_val)
    d2.text((510, row_y + 12), desc, fill="#334E68", font=font_val)

img2.save(f"{out_dir}/db_schema.png")
print("Saved db_schema.png")
