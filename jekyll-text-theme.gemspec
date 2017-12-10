# coding: utf-8

Gem::Specification.new do |spec|
  spec.name          = "jekyll-text-theme"
  spec.version       = "1.4.2"
  spec.authors       = ["kitian616"]
  spec.email         = ["kitian616@outlook.com"]

  spec.summary       = %q{A text first theme for Jekyll.}
  spec.homepage      = "https://github.com/kitian616/jekyll-TeXt-theme"
  spec.license       = "MIT"

  spec.files         = `git ls-files -z`.split("\x0").select do |f|
    f.match(%r{^((_data|_includes|_layouts|_sass|assets)/|(LICENSE|README)((\.(txt|md|markdown)|$)))}i)
  end

  spec.add_runtime_dependency "jekyll", "~> 3.5"
  spec.add_runtime_dependency "jekyll-paginate", "~> 1.1"
  spec.add_runtime_dependency "jekyll-sitemap", "~> 1.0"
  spec.add_runtime_dependency "jekyll-feed", "~> 0.9.2"
  spec.add_runtime_dependency "jemoji", "~> 0.8"

  spec.add_development_dependency "bundler", "~> 1.12"
  spec.add_development_dependency "rake", "~> 10.0"
end
