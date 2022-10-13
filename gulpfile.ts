/**
 * gulp imports
 */
import {
  src,
  task,
  dest,
  watch,
  series,
  parallel
} from 'gulp';

/**
 * typescript imports
 */
import {
  createProject
} from 'gulp-typescript'


const gulpSass = require('gulp-sass'),
    sass = gulpSass(require('sass'));

/**
 * declarations
 */


/**
 * project: the project imported from tsconfig.json
 */
const project =
    createProject('tsconfig.json');


/**
 * compile everything under build:typescript into dist/es2019
 */
task('build:typescript', () => {
  return src('./src/main/typescript/**/*.ts')
      .pipe(project())
      .pipe(dest('./dist/es2019'))
      .pipe(dest('./frontend/ts'));
});

task('watch:typescript', () => {
  return watch(
      './src/main/typescript/**/*.ts',
      series('build:typescript')
  );

});


/**
 * compile scss into dist/styles
 */

task('build:scss', () => {
  return src('./src/main/scss/**/*.scss')
      .pipe(sass().on('error', sass.logError))
      .pipe(dest('./dist/styles'))
      .pipe(dest('./frontend/dist/styles'));
});

task('watch:scss', () => {
  return watch('./src/main/scss/**/*.scss', series('build:scss'))
});

/**
 * build entire project into dist
 */
task('build',
    parallel(
        'build:typescript',
        'build:scss'
    ));


task(
    'watch',
    parallel(
        'watch:scss',
        'watch:typescript'
    ));

task('develop',
    series('build', 'watch')
);