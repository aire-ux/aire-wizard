/**
 * gulp imports
 */
import {
  src,
  task,
  dest,
  parallel
} from 'gulp';

/**
 * typescript imports
 */
import {
  createProject
} from 'gulp-typescript'



const gulpSass = require('gulp-sass');
const sass = gulpSass(require('node-sass'));

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
});


/**
 * compile scss into dist/styles
 */

task('build:scss', () => {
  return src('./src/main/scss/**/*.scss')
      .pipe(sass().on('error', sass.logError))
      .pipe(dest('./dist/styles'));
});


/**
 * build entire project into dist
 */
task('build',
    parallel(
        'build:typescript',
        'build:scss'
    ));