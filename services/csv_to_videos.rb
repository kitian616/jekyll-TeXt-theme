# frozen_string_literal: true

require 'csv'
require 'erb'
require 'byebug'

class Video < Struct.new(:id, :title)
end

class CsvToVideos < Struct.new(:csv_file_path)
  def self.run(csv_file_path = 'services/videos.csv')
    CsvToVideos.new(csv_file_path).run
  end

  def run
    videos = []
    CSV.foreach(csv_file_path, headers: true) do |csv_row|
      videos << Video.new(csv_row['id'], csv_row['title'])
    end

    File.open(jekyll_file_name, 'w') do |f|
      file_string = ERB.new(template_string).result(binding)
      puts file_string
      f.write file_string
    end
  end

  private

  def template_string
    "videos:
<% videos.each do |video| %>  -
    id: <%= video.id %>
    title: <%= video.title %>
<% end %>"
  end

  def jekyll_file_name
    '_data/videos.yml'
  end
end

CsvToVideos.run
