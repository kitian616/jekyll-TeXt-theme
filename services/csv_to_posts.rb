# frozen_string_literal: true

require 'csv'
require 'erb'
require 'byebug'

class CsvToPosts < Struct.new(:csv_row)
  def self.run(csv_file_path = 'services/posts.csv')
    # iterator-style:
    CSV.foreach(csv_file_path, headers: true) do |csv_row|
      CsvToPosts.new(csv_row).write
    end
  end

  def write
    # puts csv_row.to_h.to_s
    File.open(jekyll_file_name, 'w') do |f|
      file_string = if csv_row['type'] == 'video'
                      ERB.new(video_template_string).result(binding)
                    else
                      ERB.new(post_template_string).result(binding)
                    end
      puts file_string
      f.write file_string
    end
  end

  private

  def post_template_string
    "---
layout: article
author: <%= csv_row['author']&.strip %>
location: <%= csv_row['location']&.strip %>
tags: <%= csv_row['tags']&.strip %>
full_width: false
---
<%= csv_row['content']&.strip %>
"
  end

  def video_template_string
    "---
layout: article
author: <%= csv_row['author']&.strip %>
location: <%= csv_row['location']&.strip %>
tags: <%= csv_row['tags']&.strip %>
full_width: false
---

{%- include extensions/youtube.html id='<%= csv_row['content']&.strip %>' -%}
"
  end

  def jekyll_dir_name
    '_posts'
  end

  def jekyll_file_name
    date = Date.strptime(csv_row['date']&.strip, '%m/%d/%Y')
    formatted_date = date.strftime('%Y-%m-%d')
    author = csv_row['author']&.strip.downcase.split(' ').join('-')
    author = author.gsub('\.', '')
    "#{jekyll_dir_name}/#{formatted_date}-#{author}.md"
  end
end

CsvToPosts.run
