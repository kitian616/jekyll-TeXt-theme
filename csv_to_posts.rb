# frozen_string_literal: true

require 'csv'
require 'erb'

class CsvToPosts < Struct.new(:csv_row)
  def self.run
    # iterator-style:
    CSV.foreach('_posts/posts.csv', headers: true) do |csv_row|
      CsvToPosts.new(csv_row).write
    end
  end

  def write
    puts csv_row.to_h.to_s
    File.open(jekyll_file_name, 'w') do |f|
      file_string = ERB.new(template_string).result(binding)
      puts file_string
      # f.write file_string
    end
  end

  private

  def template_string
    "---
layout: article
author: <%= csv_row[:name].strip %>
location: <%= csv_row[:location].strip %>
tags: <%= csv_row[:tags].strip %>
full_width: false
---
<%= csv_row[:content].strip %>
"
  end

  def jekyll_dir_name
    '_posts'
  end

  def jekyll_file_name
    "#{jekyll_dir_name}/#{csv_row[:date]&.strip}-#{csv_row[:author]&.strip}.md"
  end
end

CsvToPosts.run
